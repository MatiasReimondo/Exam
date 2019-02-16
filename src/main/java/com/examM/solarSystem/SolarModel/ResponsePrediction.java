package com.examM.solarSystem.SolarModel;

public class ResponsePrediction {

    private final String message1;
    private final String message2;
    private final String message3;

    public ResponsePrediction(Integer counter_rain, Integer counter_drought, Integer counter_optimal){
        this.message1 = "La cantidad de dias con sequia en 10 años son: " + counter_drought;
        this.message2 = "La cantidad de dias con condiciones optimas en 10 años son: "+ counter_optimal;
        this.message3 = "La cantidad de dias con lluvia en 10 años son: "+ counter_rain;
    }

    public String getMessage1() {
        return message1;
    }

    public String getMessage2() {
        return message2;
    }

    public String getMessage3() {
        return message3;
    }
}
