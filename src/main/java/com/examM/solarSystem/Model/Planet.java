package com.examM.solarSystem.Model;

/**
 * Clase encargada de modelar el movimiento planetario
 */
public class Planet extends Point {

    private double angular_velocity;
    private double distance_sun;
    private double angle;
    private String name;

    /**
     * Asumimos que todos los planetas estan alineados con el sol
     * de la forma que muestra el examen     *
     * @param name
     * @param distance_sun
     * @param angular_velocity
     */
        public Planet(String name, double distance_sun,double angular_velocity ) {

        super(distance_sun,0);
        this.angle = 0;
        this.angular_velocity = angular_velocity;
        this.distance_sun = distance_sun;
        this.name = name;
    }

    /**
     * Construtor por copia
     * @param planet
     */
    public Planet(Planet planet){
        this.setCoordinate_x(planet.getCoordinate_x());
        this.setCoordinate_y(planet.getCoordinate_y());
        this.angle = planet.getAngle();
        this.angular_velocity = planet.getAngular_velocity();
        this.distance_sun = planet.getDistance_sun();
        this.name = planet.name;
    }

    /**
     * Mueve el planeta un dia hacia adelante
     * Mantiene el angulo entre el planeta y los ejes entre 0 a 360
     */
    public void forward(){
        this.angle+= angular_velocity;
        if(this.angle<360){
            this.angle+=360;
        }
        if(this.angle >360){
            this.angle-=360;
        }
        move();
    }

    /**
     * Mueve el planeta hacia atras con una velocidad angular/factor
     * @param factor
     */

    public void rewind(int factor){
        double factor_angle = this.angular_velocity/factor;
        this.angle -= factor_angle;
        move();
    }

    /**
     * Traslada el planeta en base a su angulo respecto del eje.
     */
    private void move(){

        this.setCoordinate_x(distance_sun * Math.cos(Math.toRadians(angle)));
        this.setCoordinate_y(distance_sun * Math.sin(Math.toRadians(angle)));
    }


    public double getAngular_velocity() {
        return angular_velocity;
    }

    public void setAngular_velocity(double angular_velocity) {
        this.angular_velocity = angular_velocity;
    }

    public double getDistance_sun() {
        return distance_sun;
    }

    public void setDistance_sun(double distance_sun) {
        this.distance_sun = distance_sun;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
