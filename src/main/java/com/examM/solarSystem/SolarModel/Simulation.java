package com.examM.solarSystem.SolarModel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Simulation {

    private List<Planet> solar_system = new LinkedList<Planet>();
    private Map<Integer ,Period> days = new HashMap<Integer, Period>();

    private Integer counter_rain;
    private Integer counter_drought;
    private Integer counter_optimal;


    public Simulation(){
        solar_system.add(new Planet("Ferengi",500,-1));
        solar_system.add(new Planet("Betasoide",2000,-3));
        solar_system.add(new Planet("Vulcano",1000,5));
        counter_rain = 0;
        counter_drought = 0;
        counter_optimal = 0;

    }

    public void simulate_x_days(int days){
        for (int day = 0; day < days; day++) {
            for(Planet planet: solar_system){
                planet.move();
            }
            Observation observ = new Observation(solar_system.get(0).getLocation(),
                                                 solar_system.get(1).getLocation(),
                                                 solar_system.get(2).getLocation());

            if(observ.are_planets_alinged()){
                if(observ.is_sun_alinged()){
                    this.days.put(day+1,Period.DROUGHT);
                    this.counter_drought++;
                }else{
                    this.days.put(day+1,Period.OPTIMAL);
                    this.counter_optimal++;
                }
            }
            else if(observ.contains_sun()){
                this.days.put(day+1,Period.RAIN);
                this.counter_rain++;
            }else {
                this.days.put(day+1,Period.NO_IMPORTANT);
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

    public void setCounter_optimal(Integer counter_optimal) {
        this.counter_optimal = counter_optimal;
    }
}
