package com.examM.solarSystem.Model;

import java.util.*;

/**
 * Clase encargada de generar la simulacion
 * Generando la distintas predicciones de cada dia
 */
public class Simulation {

    private List<Planet> solar_system = new LinkedList<Planet>();

    private Map<Long , Prediction> weather_in_day = new HashMap<Long, Prediction>();

    private Map<Long,Observation> observations_by_day = new HashMap<Long,Observation>();

    private Integer counter_rain;
    private Integer counter_drought;
    private Integer counter_optimal;
    private long day_max_rain;
    private double perimeterMax = 0;

    private static int FACTOR = 16;
    private static int PREDICTED_DAYS = 3650;


    /**
     * Constructor de la simulacion
     * Empezamos la simulacion con todos los planetas alineados
     * Cargamos el estado inicial del sistema
     */
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

    /**
     * Simula los 10 anios del sistema solar
     * y genera las predicciones para cada dia
     * @return lista de predicciones de cada dia
     */
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
        //Removemos el dia 0 de la prediccion
        return new ArrayList<Prediction>(weather_in_day.values()).subList(1,PREDICTED_DAYS);

    }

    /**
     * Metodo encargado de generar la prediccion por dia
     * Primero se trata de predecir de manera discreta
     * Sino se encuentra ningun patron se pasa a la prediccion aproximada.
     * Si en ninguno de los metodos se encuentra algun patron se setea el clima del dia anterior
     */
    public void predictDays(){
        for (long i = 1; i <= PREDICTED_DAYS ; i++) {
            if(!discreet_prediction(i)){
                rough_prediction(i);
            }
            continue_period(i);
            max_rain_period(i);
        }
    }

    /**
     * Metodo que realiza la prediccion del dia examinando
     * "la foto" de la observacion
     * @param day
     * @return True = Se encontro un patron en la observacion
     */
    private boolean discreet_prediction(long day){
        Observation obs = observations_by_day.get(day);
        boolean prediction_found = false;

        if(obs.contains_sun()) {
            weather_in_day.put(day, new Prediction(day, Weather.LLUVIA));
            rain_history(obs,day);
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

    /**
     * Metodo que trata de determinar a traves de aproximaciones
     * que patron se presento durante el dia
     * @param day
     */
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

    /**
     * Metodo que analiza el periodo de lluvia y cuando se presenta
     * el maximo del mismo
     * @param obs
     * @param day
     */
    private void rain_history(Observation obs,long day){
        if(weather_in_day.get(day-1).getDetail()!= Weather.LLUVIA){
            counter_rain++;
        }
        if (obs.perimeter() > perimeterMax) {
            perimeterMax = obs.perimeter();
            day_max_rain = day;
        }
    }

    /**
     * Metodo que analiza si los planetas se han alineado durante el dia
     * y si lo hicieron o no con el sol
     * @param obs Observacion del dia
     * @param day
     */
    private void alined_during_day(Observation obs,long day){
        boolean found_aline = false;
        int iterations = 0;

        Observation observation = new Observation(obs);

        while(!found_aline && iterations<FACTOR ){
            observation.rewind_planets(FACTOR);

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

    /**
     * Metodo que en el final de un periodo de lluvia
     * Setea el maximo de lluvia dentro de un periodo
     * @param day
     */
    //Si hubo un cambio de lluvia a otro clima
    //Setea el dia con mas lluvia en el periodo
    private void max_rain_period(long day){
        if((weather_in_day.get(day).getDetail()!=Weather.LLUVIA) &&
                (weather_in_day.get(day-1).getDetail() == Weather.LLUVIA)){
            weather_in_day.remove(day_max_rain);
            weather_in_day.put(day_max_rain,new Prediction(day_max_rain,Weather.MAXIMO_LLUVIA));
            perimeterMax = 0;
        }
    }

    /**
     * Metodo que informa si cambio el signo del angulo entre los planetas de dos
     * observaciones distintas
     * @param obs1
     * @param obs2
     * @returns True = el signo del angulo ha cambiado
     */
    private boolean changeSign(Observation obs1, Observation obs2){
        return (Math.signum(obs1.angleBetweenSegments()) != Math.signum(obs2.angleBetweenSegments()));
    }

    /**
     * Si no se encontro ningun patron en ninguno de los analisis
     * setea el clima del dia con el clima del dia anterior
     * ya que el periodo continua
     * @param day
     */
    private void continue_period(long day){
        if(!weather_in_day.containsKey(day)){
            weather_in_day.put(day,new Prediction(day, weather_in_day.get(day-1).getDetail()));
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
