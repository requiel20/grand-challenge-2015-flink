package com.requiel.grandchallenge;

import com.requiel.grandchallenge.scorekeeper.BucketScoreKeeper;
import com.requiel.grandchallenge.types.CellBasedTaxiTrip;
import com.requiel.grandchallenge.types.TaxiTrip;
import com.requiel.grandchallenge.types.TenMostFrequentTrips;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 */
public class Solution {

    private static Logger log = LoggerFactory.getLogger(Solution.class);

    private static String DEFAULT_INPUT_FILE = "grand-challenge-data-1000000.csv";
    private static String DEFAULT_OUTPUT_FILE = "grand-challenge-output";

    public static void main(String[] args) throws Exception {

        String input, output;

        try {
            input = ParameterTool.fromArgs(args).get("input", DEFAULT_INPUT_FILE);
        } catch (Exception e) {
            input = DEFAULT_INPUT_FILE;
        }

        try {
            output = ParameterTool.fromArgs(args).get("output", DEFAULT_OUTPUT_FILE);
        } catch (Exception e) {
            output = DEFAULT_OUTPUT_FILE;
        }

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStream<String> inputData1 = env.addSource(new RedisCheckpointedSource("192.168.56.103"))
                .setGeoLocationKey("location1");

        DataStream<CellBasedTaxiTrip> trips1 = inputData1
                .flatMap(new Parser())
                .assignTimestampsAndWatermarks(new AscendingTimestampExtractor<TaxiTrip>() {
                    @Override
                    public long extractAscendingTimestamp(TaxiTrip taxiTrip) {
                        return taxiTrip.getDropoff_datetime().atZone(ZoneId.systemDefault()).toEpochSecond();
                    }
                })
                .flatMap(new ToCellBasedTaxiTrip());


        trips1
                .process(new TopTenRoutes())
                .setParallelism(1)
                .writeAsText(output, FileSystem.WriteMode.OVERWRITE)
                .setParallelism(1);

        env.execute("Grand challenge 2015");
    }

    private static class Parser extends RichFlatMapFunction<String, TaxiTrip> {
        @Override
        public void flatMap(String line, Collector<TaxiTrip> collector) throws Exception {
            TaxiTrip trip = TaxiTrip.fromString(line);
            if (trip != null) {
                collector.collect(trip);
            }
        }
    }

    private static class RedisCheckpointedSource implements SourceFunction<String>, CheckpointedFunction {
        //index to retrieve
        private long count = 0L;

        Jedis jedis;

        private volatile boolean isRunning = true;

        //keeps the state
        private transient ListState<Long> checkpointedCount;

        public RedisCheckpointedSource(String host) {
            jedis = new Jedis(host, 6379);
        }

        @Override
        public void snapshotState(FunctionSnapshotContext functionSnapshotContext) throws Exception {
            this.checkpointedCount.clear();
            this.checkpointedCount.add(count);
        }

        @Override
        public void initializeState(FunctionInitializationContext context) throws Exception {
            this.checkpointedCount = context
                    .getOperatorStateStore()
                    .getListState(new ListStateDescriptor<>("count", Long.class));

            if (context.isRestored()) {
                for (Long count : this.checkpointedCount.get()) {
                    this.count = count;
                }
            }
        }

        @Override
        public void run(SourceContext<String> context) throws Exception {
            while (isRunning && count < 1000) {
                // this synchronized block ensures that state checkpointing,
                // internal state updates and emission of elements are an atomic operation
                synchronized (context.getCheckpointLock()) {
                    context.collect(jedis.get("" + count));
                    count++;
                }
            }
        }

        @Override
        public void cancel() {
            isRunning = false;
        }
    }

    private static class ToCellBasedTaxiTrip implements FlatMapFunction<TaxiTrip, CellBasedTaxiTrip> {

        double origin_y = toMetersY(41.474937) - 250;
        double origin_x = toMetersX(-74.913585, 41.474937) - 250;

        double toMetersX(double longitude, double latitude) {
            return longitude * 40075160d * Math.cos(Math.toRadians(latitude)) / 360d;
        }

        double toMetersY(double latitude) {
            return latitude * 40008000d / 360d;
        }


        @Override
        public void flatMap(TaxiTrip taxiTrip, Collector<CellBasedTaxiTrip> collector) throws Exception {
            double start_x = toMetersX(taxiTrip.getPickup_longitude(), taxiTrip.getPickup_latitude());
            double start_y = toMetersY(taxiTrip.getPickup_latitude());

            double end_x = toMetersX(taxiTrip.getDropoff_longitude(), taxiTrip.getDropoff_latitude());
            double end_y = toMetersY(taxiTrip.getDropoff_latitude());

            if (start_x < origin_x | start_y > origin_y | end_x < origin_x | end_y > origin_y) {
                return;
            }

            int startCellX = (int) ((start_x - origin_x) / 300) + 1;
            int startCellY = (int) ((origin_y - start_y) / 300) + 1;

            int endCellX = (int) ((start_x - origin_x) / 300) + 1;
            int endCellY = (int) ((origin_y - end_y) / 300) + 1;

            if (startCellX > 300 | endCellX > 300 | startCellY > 300 | endCellY > 300) {
                return;
            }

            collector.collect(new CellBasedTaxiTrip(startCellX + "." + startCellY, endCellX + "." + endCellY, taxiTrip.getDropoff_datetime(), taxiTrip.getPickup_datetime(), taxiTrip.getTotal_amount()));
        }
    }

    private static class TopTenRoutes extends ProcessFunction<CellBasedTaxiTrip, TenMostFrequentTrips> {
        private BucketScoreKeeper<CellBasedTaxiTrip> scoreKeeper = new BucketScoreKeeper<>(10);

        private ArrayList<CellBasedTaxiTrip> last30Minutes = new ArrayList<>();

        @Override
        public void processElement(CellBasedTaxiTrip trip, Context context, Collector<TenMostFrequentTrips> collector) throws Exception {
            last30Minutes.add(trip);

            //increase score of new entry
            if (scoreKeeper.increase(trip)) {
                collector.collect(TenMostFrequentTrips.fromList(trip, scoreKeeper.getPodium(), delay(trip)));
            }

            Iterator<CellBasedTaxiTrip> iterator = last30Minutes.iterator();

            //remove entries too old to matter
            while (iterator.hasNext()) {
                CellBasedTaxiTrip oldTrip = iterator.next();
                if (oldTrip.getEnd_time().isBefore(trip.getEnd_time().minusMinutes(30))) {
                    if (scoreKeeper.decrease(trip)) {
                        collector.collect(TenMostFrequentTrips.fromList(trip, scoreKeeper.getPodium(), delay(trip)));
                    }
                    iterator.remove();
                } else {
                    break;
                }
            }
        }

        private long delay(CellBasedTaxiTrip trip) {
            return System.currentTimeMillis() - trip.getIngestionTime();
        }

    }
}
