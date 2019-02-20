package com.examM.solarSystem.Model;

import java.awt.*;

public class Observation {

    private Planet p1;
    private Planet p2;
    private Planet p3;

    private static Point location_s = new Point(0,0);

    private boolean contains_sun;

    private double perimeter;

    private double angleSegments;

    public Observation(Planet p1, Planet p2, Planet p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;

        process_information();

    }

    private void process_information(){
        this.perimeter = perimeter();
        this.contains_sun = contains_sun();
        this.angleSegments = angleBetweenSegments();

    }

    private double perimeter(){

        double distance1 = distance(p1,p2);
        double distance2 = distance(p2,p3);
        double distance3 = distance(p3,p1);

        double perimeter = distance1 + distance2 + distance3;

        return perimeter;
    }

    public double distance(Point p1, Point p2){

        double distance = (Math.sqrt(Math.pow(p1.getCoordinate_x()-p2.getCoordinate_x(),2)
                          + Math.pow(p1.getCoordinate_y()-p2.getCoordinate_y(),2)));
        return distance;
    }


    private boolean contains_sun (){
        Polygon triangle = new Polygon();
        triangle.addPoint((int)p1.getCoordinate_x(),(int)p1.getCoordinate_y());
        triangle.addPoint((int)p2.getCoordinate_x(),(int)p2.getCoordinate_y());
        triangle.addPoint((int)p3.getCoordinate_x(),(int)p3.getCoordinate_y());

        return triangle.contains(location_s.getCoordinate_x(),location_s.getCoordinate_y());
    }

    //Tomamos como segmento P2->P1 (V->F)
    //Y P2->P3 (V->B)
    public double angleBetweenSegments(){

        Point pi = new Point(p1.getCoordinate_x()-p2.getCoordinate_x(),p1.getCoordinate_y()-p2.getCoordinate_y());
        Point pj = new Point(p3.getCoordinate_x()-p2.getCoordinate_x(),p3.getCoordinate_y()-p2.getCoordinate_y());

        double angle_pi=Math.atan2(pi.getCoordinate_x(),pi.getCoordinate_y());
        double angle_pj=Math.atan2(pj.getCoordinate_x(),pj.getCoordinate_y());

        double angle=Math.toDegrees(angle_pj-angle_pi);

        return angle;
    }


    public boolean isContains_sun() {
        return contains_sun;
    }

    public void setContains_sun(boolean contains_sun) {
        this.contains_sun = contains_sun;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(double perimeter) {
        this.perimeter = perimeter;
    }

    public double getAngleSegments() {
        return angleSegments;
    }

    public void setAngleSegments(double angleSegments) {
        this.angleSegments = angleSegments;
    }
}
