package com.examM.solarSystem.Model;

public class Planet extends Point {

    private double angular_velocity;
    private double distance_sun;
    private double angle;

    private String name;

    //Asumimos que todos los planetas estan alineados con el sol
    //de la forma que muestra el examen
    public Planet(String name, double distance_sun,double angular_velocity ) {

        super(distance_sun,0);
        //Alineados con angulo 0
        this.angle = 0;
        this.angular_velocity = angular_velocity;
        this.distance_sun = distance_sun;
        this.name = name;
    }

    public Planet(Planet planet){
        this.setCoordinate_x(planet.getCoordinate_x());
        this.setCoordinate_y(planet.getCoordinate_y());
        this.angle = planet.getAngle();
        this.angular_velocity = planet.getAngular_velocity();
        this.distance_sun = planet.getDistance_sun();
        this.name = planet.name;
    }




    public void forward(){
        this.angle+= angular_velocity;
        move();
    }

    public void rewind(int factor){
        this.setCoordinate_x(this.getCoordinate_x() - distance_sun * Math.cos(Math.toRadians(angle))/factor);
        this.setCoordinate_y(this.getCoordinate_y() - distance_sun * Math.sin(Math.toRadians(angle))/factor);
    }

    private void move(){
        this.setCoordinate_x(this.getCoordinate_x() + distance_sun * Math.cos(Math.toRadians(angle)));
        this.setCoordinate_y(this.getCoordinate_y() + distance_sun * Math.sin(Math.toRadians(angle)));
    }

    public void roundError(){
        this.setCoordinate_x(Math.round(this.getCoordinate_x()));
        this.setCoordinate_y(Math.round(this.getCoordinate_y()));
    }

    public void roundForward(){
        this.forward();
        this.roundError();
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
