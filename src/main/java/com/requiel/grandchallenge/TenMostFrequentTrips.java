package com.requiel.grandchallenge;

import java.time.LocalDateTime;

public class TenMostFrequentTrips {
    private LocalDateTime pickup_datetime;
    private LocalDateTime dropoff_datetime;

    private String start_cell_id_1;
    private String end_cell_id_1;

    private String start_cell_id_2;
    private String end_cell_id_2;

    private String start_cell_id_3;
    private String end_cell_id_3;

    private String start_cell_id_4;
    private String end_cell_id_4;

    private String start_cell_id_5;
    private String end_cell_id_5;

    private String start_cell_id_6;
    private String end_cell_id_6;

    private String start_cell_id_7;
    private String end_cell_id_7;

    private String start_cell_id_8;
    private String end_cell_id_8;

    private String start_cell_id_9;
    private String end_cell_id_9;

    private String start_cell_id_10;
    private String end_cell_id_10;

    private double delay;

    public TenMostFrequentTrips(LocalDateTime pickup_datetime, LocalDateTime dropoff_datetime, String start_cell_id_1, String end_cell_id_1, String start_cell_id_2, String end_cell_id_2, String start_cell_id_3, String end_cell_id_3, String start_cell_id_4, String end_cell_id_4, String start_cell_id_5, String end_cell_id_5, String start_cell_id_6, String end_cell_id_6, String start_cell_id_7, String end_cell_id_7, String start_cell_id_8, String end_cell_id_8, String start_cell_id_9, String end_cell_id_9, String start_cell_id_10, String end_cell_id_10, double delay) {
        this.pickup_datetime = pickup_datetime;
        this.dropoff_datetime = dropoff_datetime;
        this.start_cell_id_1 = start_cell_id_1;
        this.end_cell_id_1 = end_cell_id_1;
        this.start_cell_id_2 = start_cell_id_2;
        this.end_cell_id_2 = end_cell_id_2;
        this.start_cell_id_3 = start_cell_id_3;
        this.end_cell_id_3 = end_cell_id_3;
        this.start_cell_id_4 = start_cell_id_4;
        this.end_cell_id_4 = end_cell_id_4;
        this.start_cell_id_5 = start_cell_id_5;
        this.end_cell_id_5 = end_cell_id_5;
        this.start_cell_id_6 = start_cell_id_6;
        this.end_cell_id_6 = end_cell_id_6;
        this.start_cell_id_7 = start_cell_id_7;
        this.end_cell_id_7 = end_cell_id_7;
        this.start_cell_id_8 = start_cell_id_8;
        this.end_cell_id_8 = end_cell_id_8;
        this.start_cell_id_9 = start_cell_id_9;
        this.end_cell_id_9 = end_cell_id_9;
        this.start_cell_id_10 = start_cell_id_10;
        this.end_cell_id_10 = end_cell_id_10;
        this.delay = delay;
    }

    public LocalDateTime getPickup_datetime() {
        return pickup_datetime;
    }

    public LocalDateTime getDropoff_datetime() {
        return dropoff_datetime;
    }

    public String getStart_cell_id_1() {
        return start_cell_id_1;
    }

    public String getEnd_cell_id_1() {
        return end_cell_id_1;
    }

    public String getStart_cell_id_2() {
        return start_cell_id_2;
    }

    public String getEnd_cell_id_2() {
        return end_cell_id_2;
    }

    public String getStart_cell_id_3() {
        return start_cell_id_3;
    }

    public String getEnd_cell_id_3() {
        return end_cell_id_3;
    }

    public String getStart_cell_id_4() {
        return start_cell_id_4;
    }

    public String getEnd_cell_id_4() {
        return end_cell_id_4;
    }

    public String getStart_cell_id_5() {
        return start_cell_id_5;
    }

    public String getEnd_cell_id_5() {
        return end_cell_id_5;
    }

    public String getStart_cell_id_6() {
        return start_cell_id_6;
    }

    public String getEnd_cell_id_6() {
        return end_cell_id_6;
    }

    public String getStart_cell_id_7() {
        return start_cell_id_7;
    }

    public String getEnd_cell_id_7() {
        return end_cell_id_7;
    }

    public String getStart_cell_id_8() {
        return start_cell_id_8;
    }

    public String getEnd_cell_id_8() {
        return end_cell_id_8;
    }

    public String getStart_cell_id_9() {
        return start_cell_id_9;
    }

    public String getEnd_cell_id_9() {
        return end_cell_id_9;
    }

    public String getStart_cell_id_10() {
        return start_cell_id_10;
    }

    public String getEnd_cell_id_10() {
        return end_cell_id_10;
    }

    public double getDelay() {
        return delay;
    }
}
