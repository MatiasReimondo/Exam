package com.examM.solarSystem.Model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Simulation {

    private List<Planet> solar_system = new LinkedList<Planet>();
    private Map<Long ,PeriodPrediction> days = new HashMap<Long, PeriodPrediction>();

    private Map<Long ,PeriodPrediction> days_in_cicle = new HashMap<Long, PeriodPrediction>();

    private List<Observation> cicle = new LinkedList<Observation>();



    private Integer counter_rain;
    private Integer counter_drought;
    private Integer counter_optimal;
    private long day_max_rain;

    private static double ERROR_ANGLE= 5;
    private static double ERROR_DISTANCE =10;
    private static double ANGLE_ALINE_ZERO = 0;
    private static double ANGLE_ALINE_PI = 180;
    private static double ANGLE_ALINE_MINUS_PI = -180;
    private static int LMT_ITERATIONS = 10;
    private static int FACTOR = 10;


    public Simulation(){
        solar_system.add(new Planet("Ferengi",500,-1));
        solar_system.add(new Planet("Vulcano",1000,5));
        solar_system.add(new Planet("Betasoide",2000,-3));

        counter_rain = 0;
        counter_drought = 0;
        counter_optimal = 0;

    }

    public void simulateCicle(){
        for (int i = 0; i <3650 ; i++) {

            Planet p1;
            Planet p2;
            Planet p3;
            for (Planet planet:solar_system) {
                planet.roundForward();
            }
            p1 = new Planet(solar_system.get(0));
            p2 = new Planet(solar_system.get(1));
            p3 = new Planet(solar_system.get(2));

            cicle.add(i,new Observation(p1,p2,p3));

        }
        predictCicle();
    }

    public void predictCicle(){
        double perimeterMax =0 ;
        for (long i = 0; i <3650 ; i++) {
            Observation obs = cicle.get((int)i);
            if(obs.contains_sun()){
                counter_rain++;
                days_in_cicle.put(i+1,new PeriodPrediction(i+1,Period.RAIN));
                if(obs.perimeter()>perimeterMax){
                    perimeterMax = obs.perimeter();
                    day_max_rain = i+1;
                }
            }else if(check_aline(obs.angleBetweenSegments())){
                if(obs.alined_with_sun(ERROR_DISTANCE)){
                    counter_drought++;
                    days_in_cicle.put(i+1,new PeriodPrediction(i+1,Period.DROUGHT));
                }else{
                    counter_optimal++;
                    days_in_cicle.put(i+1,new PeriodPrediction(i+1,Period.OPTIMAL));
                }
            }else{
                Observation obs_past = cicle.get((int)i-1);
                if(Long.signum((long) obs_past.angleBetweenSegments())!= Long.signum((long)obs.angleBetweenSegments())){
                    alined_during_day(obs,i+1);
                }else{
                    days_in_cicle.put(i+1,new PeriodPrediction(i+1,days_in_cicle.get(i).getDetail()));
                }
            }
        }
        days_in_cicle.remove(day_max_rain);
        days_in_cicle.put(day_max_rain,new PeriodPrediction(day_max_rain,Period.RAIN_MAX));
        System.out.println("Prediccion Terminada");

    }

    private boolean check_aline(double angle){

        boolean aline_0 = ((ANGLE_ALINE_ZERO-ERROR_ANGLE < angle) && (angle <ANGLE_ALINE_ZERO+ERROR_ANGLE));
        boolean aline_pi = ((ANGLE_ALINE_PI-ERROR_ANGLE <angle ));
        boolean aline_minus_pi = ((angle < ANGLE_ALINE_MINUS_PI+ERROR_ANGLE));

        return (aline_0 || aline_pi || aline_minus_pi);
    }

    private void alined_during_day(Observation obs,long day){
        boolean found_aline = false;
        int iterations = 0;
        Observation observation = new Observation(obs);
        while(!found_aline && iterations<LMT_ITERATIONS ){
            observation.getP1().rewind(FACTOR);
            observation.getP2().rewind(FACTOR);
            observation.getP3().rewind(FACTOR);

            if(check_aline(observation.angleBetweenSegments())){
                found_aline = true;
                if(observation.alined_with_sun(ERROR_DISTANCE)){
                    counter_drought++;
                    days_in_cicle.put(day,new PeriodPrediction(day,Period.OPTIMAL));
                }else{
                    counter_optimal++;
                    days_in_cicle.put(day,new PeriodPrediction(day,Period.OPTIMAL));
                }
            }
            iterations++;
        }
        if(!found_aline){
            days_in_cicle.put(day,new PeriodPrediction(day,days_in_cicle.get(day-1).getDetail()));
        }
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
