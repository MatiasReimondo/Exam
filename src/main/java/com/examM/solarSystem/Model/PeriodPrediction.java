package com.examM.solarSystem.Model;

public class PeriodPrediction {

    private final long day;
    private final Weather detail;

    public PeriodPrediction(long day, Weather detail){
        this.day= day;
        this.detail = detail;
    }

    public long getDay() {
        return day;
    }

    public Weather getDetail() {
        return detail;
    }


}
