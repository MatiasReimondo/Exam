package com.examM.solarSystem.Model;

/**
 * Clase encargada de formar el mensaje inicial de la aplicacion
 * que informa los resultados de la prediccion
 */
public class ResponsePrediction {

    private final String message1;
    private final String message2;
    private final String message3;
    private final String message4;

    public ResponsePrediction(Integer counter_rain, Integer counter_drought, Integer counter_optimal, long rain_max_day){
        this.message1 = "La cantidad de periodos de sequia en 10 años son: " + counter_drought;
        this.message2 = "La cantidad de periodos con condiciones optimas en 10 años son: "+ counter_optimal;
        this.message3 = "La cantidad de periodos de lluvia en 10 años son: "+ counter_rain;
        this.message4 = "El dia con mas lluvia fue el dia: " + rain_max_day;
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

    public String getMessage4() {
        return message4;
    }
}
