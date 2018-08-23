package com.requiel.grandchallenge.types;

import java.time.LocalDateTime;

public class CellBasedTaxiTrip {

    private String start_cell_id;

    private String end_cell_id;

    private LocalDateTime end_time;

    private LocalDateTime start_time;

    private long ingestionTime;

    private double total_amount;

    private long delay;


    public CellBasedTaxiTrip(String start_cell_id, String end_cell_id, LocalDateTime end_time, LocalDateTime start_time, double total_amount) {
        if (start_cell_id == null || end_cell_id == null | end_time == null | start_time == null) {
            throw new IllegalArgumentException("null fields not permitted here");
        }
        this.start_cell_id = start_cell_id;
        this.end_cell_id = end_cell_id;
        this.end_time = end_time;
        this.start_time = start_time;

        if(total_amount <= 0) {
            throw new IllegalArgumentException("total amount must be positive");
        }
        this.total_amount = total_amount;
        this.ingestionTime = System.currentTimeMillis();
    }

    public String getStart_cell_id() {
        return start_cell_id;
    }

    public String getEnd_cell_id() {
        return end_cell_id;
    }

    public LocalDateTime getEnd_time() {
        return end_time;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CellBasedTaxiTrip that = (CellBasedTaxiTrip) o;

        if (!start_cell_id.equals(that.start_cell_id)) return false;
        return end_cell_id.equals(that.end_cell_id);
    }

    @Override
    public int hashCode() {
        int result = start_cell_id.hashCode();
        result = 31 * result + end_cell_id.hashCode();
        return result;
    }

    public long getIngestionTime() {
        return ingestionTime;
    }

    public void calculateDelay() {
        this.delay = System.currentTimeMillis() - ingestionTime;
    }

    public long getDelay() {
        return delay;
    }

    @Override
    public String toString() {
        return "CellBasedTaxiTrip{" +
                "start_cell_id='" + start_cell_id + '\'' +
                ", end_cell_id='" + end_cell_id + '\'' +
                ", end_time=" + end_time +
                ", start_time=" + start_time +
                ", ingestionTime=" + ingestionTime +
                ", total_amount=" + total_amount +
                ", delay=" + delay +
                '}';
    }
}
