package com.examM.solarSystem.Model;

import java.util.*;

public class Simulation {

    private List<Planet> solar_system = new LinkedList<Planet>();
    private Map<Long , Prediction> days = new HashMap<Long, Prediction>();

    private Map<Long , Prediction> weather_in_day = new HashMap<Long, Prediction>();

    private Map<Long,Observation> observations_by_day = new HashMap<Long,Observation>();

    private Integer counter_rain;
    private Integer counter_drought;
    private Integer counter_optimal;
    private long day_max_rain;
    private double perimeterMax = 0;

    private static int FACTOR = 16;
    private static int PREDICTED_DAYS = 3650;


    //Empezamos la simulacion con todos los planetas alineados
    //Cargamos el estado inicial del sistema
    public Simulation(){
        solar_system.add(new Planet("Ferengi",500,-1));
        solar_system.add(new Planet("Vulcano",1000,5));
        solar_system.add(new Planet("Betasoide",2000,-3));

        counter_rain = 0;
        counter_drought = 0;
        counter_optimal = 0;

        observations_by_day.put((long)0,new Observation(solar_system.get(0),solar_system.get(1),solar_system.get(2)));
        weather_in_day.put((long)0,new Prediction(0,Weather.SEQUIA));

    }

    public List<Prediction> simulateDays(){
        for (long i = 1; i <= PREDICTED_DAYS ; i++) {
            Planet p1;
            Planet p2;
            Planet p3;
            for (Planet planet:solar_system) {
                planet.forward();
            }
            p1 = new Planet(solar_system.get(0));
            p2 = new Planet(solar_system.get(1));
            p3 = new Planet(solar_system.get(2));

            observations_by_day.put(i,new Observation(p1,p2,p3));

        }
        predictDays();
        return new ArrayList<Prediction>(weather_in_day.values());

    }

    //Primero se trata de predecir de manera discreta
    //Se examina "La foto" de la observacion
    //Sino se encuentra ningun patron se pasa a la prediccion
    //aproximada. Esta ultima si los planetas se han cruzado
    //tratara de "rebobinar los planetas" hasta acercarse al cruce
    // y luego,tomando en cuenta un error,asignara si en la alineacion estaba o no
    //incluido el sol
    public void predictDays(){
        for (long i = 1; i <= PREDICTED_DAYS ; i++) {
            if(!discreet_prediction(i)){
                rough_prediction(i);
            }
            if(!weather_in_day.containsKey(i)){
                weather_in_day.put(i,new Prediction(i, weather_in_day.get(i-1).getDetail()));
            }
        }
        day_max_rain_cicle();
    }

    private boolean discreet_prediction(long day){
        Observation obs = observations_by_day.get(day);
        boolean prediction_found = false;

        if(obs.contains_sun()) {
            counter_rain++;
            weather_in_day.put(day, new Prediction(day, Weather.LLUVIA));
            if (obs.perimeter() > perimeterMax) {
                perimeterMax = obs.perimeter();
                day_max_rain = day;
            }
            prediction_found= true;
        }else if(obs.check_aline()){
            if(obs.alined_with_sun()){
                counter_drought++;
                weather_in_day.put(day,new Prediction(day, Weather.SEQUIA));
                prediction_found= true;
            }else{
                counter_optimal++;
                weather_in_day.put(day,new Prediction(day,Weather.CONDICIONES_OPTIMAS));
                prediction_found= true;
            }
        }
        return prediction_found;
    }

    private void rough_prediction(long day){
        Observation obs_past = observations_by_day.get((day-1));
        Observation obs = observations_by_day.get(day);
        if(obs_past.angleBetweenSegments()!= 180 &&
                obs_past.angleBetweenSegments() != -180 &&
                obs_past.angleBetweenSegments() != 0){
            if(changeSign(obs,obs_past)){
                alined_during_day(obs,day);
            }
        }
    }


    private void alined_during_day(Observation obs,long day){
        boolean found_aline = false;
        int iterations = 0;

        Observation observation = new Observation(obs);

        while(!found_aline && iterations<FACTOR ){

            observation.getP1().rewind(FACTOR);
            observation.getP2().rewind(FACTOR);
            observation.getP3().rewind(FACTOR);

            if(observation.check_aline()){
                found_aline = true;
                if(observation.alined_with_sun()){
                    counter_drought++;
                    weather_in_day.put(day,new Prediction(day,Weather.SEQUIA));
                }else{
                    counter_optimal++;
                    weather_in_day.put(day,new Prediction(day,Weather.CONDICIONES_OPTIMAS));
                }
            }
            iterations++;
        }
    }

    //El modelo repite su comportamiento cada 360 dias
    //Solo se detecta el primer dia con maxima lluvia
    //Este metodo coloca los distintos dias con un maximo de lluvia
    //En los distintos ciclos de 360 dias
    private void day_max_rain_cicle(){
        weather_in_day.remove(day_max_rain);
        weather_in_day.put(day_max_rain,new Prediction(day_max_rain,Weather.MAXIMO_LLUVIA));

        long rain_days =day_max_rain;
        while(rain_days<= PREDICTED_DAYS){
            rain_days+=360;
            weather_in_day.remove(rain_days);
            weather_in_day.put(rain_days,new Prediction(rain_days,Weather.MAXIMO_LLUVIA));

        }


    }

    private boolean changeSign(Observation obs1, Observation obs2){
        return (Math.signum(obs1.angleBetweenSegments()) != Math.signum(obs2.angleBetweenSegments()));
    }


    public Integer getCounter_rain() {
        return counter_rain;
    }

    public void setCounter_rain(Integer counter_rain) {
        this.counter_rain = counter_rain;
    }

    public Integer getCounter_drought() {
        return counter_drought;
    }

    public void setCounter_drought(Integer counter_drought) {
        this.counter_drought = counter_drought;
    }

    public Integer getCounter_optimal() {
        return counter_optimal;
    }

    public Map<Long, Prediction> getDays() {
        return days;
    }

    public void setDays(Map<Long, Prediction> days) {
        this.days = days;
    }

    public long getDay_max_rain() {
        return day_max_rain;
    }

    public void setDay_max_rain(long day_max_rain) {
        this.day_max_rain = day_max_rain;
    }

    public Map<Long, Prediction> getWeather_in_day() {
        return weather_in_day;
    }

    public void setWeather_in_day(Map<Long, Prediction> weather_in_day) {
        this.weather_in_day = weather_in_day;
    }
}
