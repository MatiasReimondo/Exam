package com.examM.solarSystem.SolarModel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Simulation {

    private List<Planet> solar_system = new LinkedList<Planet>();
    private Map<Long ,PeriodPrediction> days = new HashMap<Long, PeriodPrediction>();

    private Integer counter_rain;
    private Integer counter_drought;
    private Integer counter_optimal;
    private long day_max_rain;


    public Simulation(){
        solar_system.add(new Planet("Ferengi",500,-1));
        solar_system.add(new Planet("Betasoide",2000,-3));
        solar_system.add(new Planet("Vulcano",1000,5));
        counter_rain = 0;
        counter_drought = 0;
        counter_optimal = 0;

    }

    public void simulate_x_days(int days){
        double perimeter_max = 0;
        for (long day = 1; day <= days; day++) {
            for(Planet planet: solar_system){
                planet.move();
            }
            Observation observ = new Observation(solar_system.get(0).getLocation(),
                                                 solar_system.get(1).getLocation(),
                                                 solar_system.get(2).getLocation());

            if(observ.are_planets_alinged()){
                if(observ.is_sun_alinged()){
                    this.days.put(day,new PeriodPrediction(day,Period.DROUGHT));
                    this.counter_drought++;
                }else{
                    this.days.put(day,new PeriodPrediction(day,Period.OPTIMAL));
                    this.counter_optimal++;
                }
            }
            else if(observ.contains_sun()){
                this.days.put(day,new PeriodPrediction(day,Period.RAIN));
                this.counter_rain++;
                if(perimeter_max < observ.getTriangle_perimeter()){
                    perimeter_max = observ.getTriangle_perimeter();
                    this.day_max_rain = day;
                }
            }else {
                this.days.put(day+1,new PeriodPrediction(day+1,Period.NO_IMPORTANT));
            }
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
}
