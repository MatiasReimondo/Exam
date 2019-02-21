package com.examM.solarSystem.Model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Simulation {

    private List<Planet> solar_system = new LinkedList<Planet>();
    private Map<Long ,PeriodPrediction> days = new HashMap<Long, PeriodPrediction>();

    private Map<Long ,PeriodPrediction> days_in_cicle = new HashMap<Long, PeriodPrediction>();

    private Map<Long,Observation> cicle = new HashMap<Long,Observation>();



    private Integer counter_rain;
    private Integer counter_drought;
    private Integer counter_optimal;
    private long day_max_rain;
    private double perimeterMax = 0;

    private static double ERROR_DISTANCE =10;
    private static int FACTOR = 16;


    public Simulation(){
        solar_system.add(new Planet("Ferengi",500,-1));
        solar_system.add(new Planet("Vulcano",1000,5));
        solar_system.add(new Planet("Betasoide",2000,-3));

        counter_rain = 0;
        counter_drought = 0;
        counter_optimal = 0;

        cicle.put((long)0,new Observation(solar_system.get(0),solar_system.get(1),solar_system.get(2)));
        days_in_cicle.put((long)0,new PeriodPrediction(0,Period.DROUGHT));

    }

    public void simulateCicle(){

        for (long i = 1; i <=360 ; i++) {
            Planet p1;
            Planet p2;
            Planet p3;
            for (Planet planet:solar_system) {
                planet.forward();
            }
            p1 = new Planet(solar_system.get(0));
            p2 = new Planet(solar_system.get(1));
            p3 = new Planet(solar_system.get(2));

            cicle.put(i,new Observation(p1,p2,p3));

        }
        predictCicle();
    }

    public void predictCicle(){
        for (long i = 1; i <=360 ; i++) {
            if(!discreet_prediction(i)){
                approximate_prediction(i);
            }
            if(!days_in_cicle.containsKey(i)){
                days_in_cicle.put(i,new PeriodPrediction(i,days_in_cicle.get(i-1).getDetail()));
            }
        }
        day_max_rain_cicle();
    }

    private boolean discreet_prediction(long day){
        Observation obs = cicle.get(day);
        boolean prediction_found = false;

        if(obs.contains_sun()) {
            counter_rain++;
            days_in_cicle.put(day, new PeriodPrediction(day, Period.RAIN));
            if (obs.perimeter() > perimeterMax) {
                perimeterMax = obs.perimeter();
                day_max_rain = day;
            }
            prediction_found= true;
        }else if(obs.check_aline()){
            if(obs.alined_with_sun(ERROR_DISTANCE)){
                counter_drought++;
                days_in_cicle.put(day,new PeriodPrediction(day,Period.DROUGHT));
                prediction_found= true;
            }else{
                counter_optimal++;
                days_in_cicle.put(day,new PeriodPrediction(day,Period.OPTIMAL));
                prediction_found= true;
            }
        }
        return prediction_found;
    }

    private void approximate_prediction(long day){
        Observation obs_past = cicle.get((day-1));
        Observation obs = cicle.get(day);
        if(obs_past.angleBetweenSegments()!= 180 && obs_past.angleBetweenSegments() != -180 && obs_past.angleBetweenSegments() != 0){
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
                if(observation.alined_with_sun(ERROR_DISTANCE)){
                    counter_drought++;
                    days_in_cicle.put(day,new PeriodPrediction(day,Period.DROUGHT));
                }else{
                    counter_optimal++;
                    days_in_cicle.put(day,new PeriodPrediction(day,Period.OPTIMAL));
                }
            }
            iterations++;
        }
    }

    private void day_max_rain_cicle(){
        days_in_cicle.remove(day_max_rain);
        days_in_cicle.put(day_max_rain,new PeriodPrediction(day_max_rain,Period.RAIN_MAX));
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

    public Map<Long, PeriodPrediction> getDays() {
        return days;
    }

    public void setDays(Map<Long, PeriodPrediction> days) {
        this.days = days;
    }

    public long getDay_max_rain() {
        return day_max_rain;
    }

    public void setDay_max_rain(long day_max_rain) {
        this.day_max_rain = day_max_rain;
    }

    public Map<Long, PeriodPrediction> getDays_in_cicle() {
        return days_in_cicle;
    }

    public void setDays_in_cicle(Map<Long, PeriodPrediction> days_in_cicle) {
        this.days_in_cicle = days_in_cicle;
    }
}
