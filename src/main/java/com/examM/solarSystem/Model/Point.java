package com.examM.solarSystem.Model;

public class Point {
    private double coordinate_x;
    private double coordinate_y;

    public Point(){
        this.coordinate_x = 0;
        this.coordinate_y = 0;
    }

    public Point(double coordinate_x, double coordinate_y) {
        this.coordinate_x = coordinate_x;
        this.coordinate_y = coordinate_y;
    }

    public Point(Point point){
        this.coordinate_x = point.coordinate_x;
        this.coordinate_y = point.coordinate_y;
    }

    public double getCoordinate_x() {
        return coordinate_x;
    }

    public void setCoordinate_x(double coordinate_x) {
        this.coordinate_x = coordinate_x;
    }

    public double getCoordinate_y() {
        return coordinate_y;
    }

    public void setCoordinate_y(double coordinate_y) {
        this.coordinate_y = coordinate_y;
    }
}
