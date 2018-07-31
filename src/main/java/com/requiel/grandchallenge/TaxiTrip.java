package com.requiel.grandchallenge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TaxiTrip {
    private static Logger log = LoggerFactory.getLogger(TaxiTrip.class);

    public static TaxiTrip fromString(String line) {
        String[] fields = line.split(",");
        if (fields.length != 17) {
            log.error("Parsing error. Line had {} fields instead of 17", fields.length);
            return null;
        }

        //dates are in form YYYY-MM-DD HH:mm:SS
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");

        PayementType payementType;
        if (fields[10].equals("CRD")) {
            payementType = PayementType.CRD;
        } else if (fields[10].equals("CSH")) {
            payementType = PayementType.CSH;
        } else {
            log.error("Parsing error. Unknown payement type: {}", fields[10]);
            return null;
        }

        try {
            return new TaxiTrip(
                    fields[0],
                    fields[1],
                    LocalDateTime.parse(fields[2], formatter),
                    LocalDateTime.parse(fields[3], formatter),
                    Long.parseLong(fields[4]),
                    Double.parseDouble(fields[5]),
                    Double.parseDouble(fields[6]),
                    Double.parseDouble(fields[7]),
                    Double.parseDouble(fields[8]),
                    Double.parseDouble(fields[9]),
                    payementType,
                    Double.parseDouble(fields[11]),
                    Double.parseDouble(fields[12]),
                    Double.parseDouble(fields[13]),
                    Double.parseDouble(fields[14]),
                    Double.parseDouble(fields[15]),
                    Double.parseDouble(fields[16])
            );
        } catch (DateTimeParseException| NumberFormatException e) {
            log.error("Parsing error.");
            e.printStackTrace();
            return null;
        }

    }

    /**
     * an md5sum of the identifier of the taxi - vehicle bound
     */
    private String medallion;

    /**
     * an md5sum of the identifier for the taxi license
     */
    private String hack_license;

    /**
     * time when the passenger(s) were picked up
     */
    private LocalDateTime pickup_datetime;

    /**
     * time when the passenger(s) were dropped off
     */
    private LocalDateTime dropoff_datetime;

    /**
     * duration of the trip
     */
    private long trip_time_in_secs;

    /**
     * trip distance in miles
     */
    private double trip_distance;

    /**
     * longitude coordinate of the pickup location
     */
    private double pickup_longitude;

    /**
     * latitude coordinate of the pickup location
     */
    private double pickup_latitude;

    /**
     * longitude coordinate of the drop-off location
     */
    private double dropoff_longitude;

    /**
     * latitude coordinate of the drop-off location
     */
    private double dropoff_latitude;

    /**
     * the payment method - credit card or cash
     */
    private PayementType payment_type;

    /**
     * fare amount in dollars
     */
    private double fare_amount;

    /**
     * surcharge in dollars
     */
    private double surcharge;

    /**
     * tax in dollars
     */
    private double mta_tax;

    /**
     * tip in dollars
     */
    private double tip_amount;

    /**
     * bridge and tunnel tolls in dollars
     */
    private double tolls_amount;

    /**
     * total paid amount in dollars
     */
    private double total_amount;

    public TaxiTrip(String medallion, String hack_license, LocalDateTime pickup_datetime, LocalDateTime dropoff_datetime, long trip_time_in_secs, double trip_distance, double pickup_longitude, double pickup_latitude, double dropoff_longitude, double dropoff_latitude, PayementType payment_type, double fare_amount, double surcharge, double mta_tax, double tip_amount, double tolls_amount, double total_amount) {
        this.medallion = medallion;
        this.hack_license = hack_license;
        this.pickup_datetime = pickup_datetime;
        this.dropoff_datetime = dropoff_datetime;
        this.trip_time_in_secs = trip_time_in_secs;
        this.trip_distance = trip_distance;
        this.pickup_longitude = pickup_longitude;
        this.pickup_latitude = pickup_latitude;
        this.dropoff_longitude = dropoff_longitude;
        this.dropoff_latitude = dropoff_latitude;
        this.payment_type = payment_type;
        this.fare_amount = fare_amount;
        this.surcharge = surcharge;
        this.mta_tax = mta_tax;
        this.tip_amount = tip_amount;
        this.tolls_amount = tolls_amount;
        this.total_amount = total_amount;
    }

    public String getMedallion() {
        return medallion;
    }

    public String getHack_license() {
        return hack_license;
    }

    public LocalDateTime getPickup_datetime() {
        return pickup_datetime;
    }

    public LocalDateTime getDropoff_datetime() {
        return dropoff_datetime;
    }

    public long getTrip_time_in_secs() {
        return trip_time_in_secs;
    }

    public double getTrip_distance() {
        return trip_distance;
    }

    public double getPickup_longitude() {
        return pickup_longitude;
    }

    public double getPickup_latitude() {
        return pickup_latitude;
    }

    public double getDropoff_longitude() {
        return dropoff_longitude;
    }

    public double getDropoff_latitude() {
        return dropoff_latitude;
    }

    public PayementType getPayment_type() {
        return payment_type;
    }

    public double getFare_amount() {
        return fare_amount;
    }

    public double getSurcharge() {
        return surcharge;
    }

    public double getMta_tax() {
        return mta_tax;
    }

    public double getTip_amount() {
        return tip_amount;
    }

    public double getTolls_amount() {
        return tolls_amount;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    @Override
    public String toString() {
        String out = "";
        out += "medallion: " + medallion;
        out += " hack_license: " + hack_license;
        out += " pickup_datetime: " + pickup_datetime;
        out += " dropoff_datetime: " + dropoff_datetime;
        out += " trip_time_in_secs: " + trip_time_in_secs;
        out += " trip_distance: " + trip_distance;
        out += " pickup_longitude: " + pickup_longitude;
        out += " pickup_latitude: " + pickup_latitude;
        out += " dropoff_longitude: " + dropoff_longitude;
        out += " dropoff_latitude: " + dropoff_latitude;
        out += " payment_type: " + payment_type;
        out += " fare_amount: " + fare_amount;
        out += " surcharge: " + surcharge;
        out += " mta_tax: " + mta_tax;
        out += " tip_amount: " + tip_amount;
        out += " tolls_amount: " + tolls_amount;
        out += " total_amount: " + total_amount;
        return out;
    }
}
