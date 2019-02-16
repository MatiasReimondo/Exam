package com.examM.solarSystem.SolarModel;

public class Planet {

    private Point location;
    private double angular_velocity;
    private double distance_sun;
    private double angle;

    private String name;

    //Asumimos que todos los planetas estan alineados con el sol
    //de la forma que muestra el examen
    public Planet(String name, double distance_sun,double angular_velocity ) {

        Point location = new Point(distance_sun,0);
        this.location = location;

        //Alineados con angulo 0
        this.angle = 0;
        this.angular_velocity = angular_velocity;
        this.distance_sun = distance_sun;

        this.name = name;
    }

    //Asumimos que todos los planetas estan alineados con el sol
    //de la forma que muestra el examen
    public Planet(String name, double distance_sun,double angular_velocity, double angle ) {

        Point location = new Point(distance_sun,0);
        this.location = location;

        //Alineados con angulo 0
        this.angle = angle;
        this.angular_velocity = angular_velocity;
        this.distance_sun = distance_sun;

        this.name = name;
    }

    //Movimiento angular
    //Redondeado por unidades
    public void move(){
        this.angle += angular_velocity;
        this.location.setCoordinate_x(Math.round(this.location.getCoordinate_x() +distance_sun* Math.cos(Math.toRadians(angle))));
        this.location.setCoordinate_y(Math.round(this.location.getCoordinate_y() +distance_sun *Math.sin(Math.toRadians(angle))));
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
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
