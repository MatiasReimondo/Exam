package com.examM.solarSystem.Model;

public class PeriodPrediction {

    private final long day;
    private final Period detail;

    public PeriodPrediction(long day, Period detail){
        this.day= day;
        this.detail = detail;
    }

    public long getDay() {
        return day;
    }

    public Period getDetail() {
        return detail;
    }


}
