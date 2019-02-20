package com.examM.solarSystem.Model;

import java.awt.*;

public class Observation {

    private Planet p1;
    private Planet p2;
    private Planet p3;

    private static Point sun = new Point(0,0);


    public Observation(Planet p1, Planet p2, Planet p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public Observation (Observation observation){
        this.p1 = observation.p1;
        this.p2 = observation.p2;
        this.p3 = observation.p3;
    }

    public boolean alined_with_sun(double error){

        double dst_sun_p1 = distance(sun,p1);
        double dst_sun_p2 = distance(sun,p2);
        double dst_sun_p3 = distance(sun,p3);

        double dst_p1_p2 = distance(p1,p2);
        double dst_p2_p3 = distance(p2,p3);
        double dst_p1_p3 = distance(p1,p3);

        double coef1 =Math.abs(dst_sun_p2 -(dst_sun_p1+dst_p1_p2));
        double coef2 = Math.abs(dst_sun_p3 -(dst_sun_p2+dst_p2_p3));
        double coef3 = Math.abs(dst_sun_p3 -(dst_sun_p1+dst_p1_p3));

        if(coef1 <error && coef2<error && coef3 <error){
            return true;
        }else{
            return false;
        }
    }

    public double perimeter(){

        double distance1 = distance(p1,p2);
        double distance2 = distance(p2,p3);
        double distance3 = distance(p3,p1);

        double perimeter = distance1 + distance2 + distance3;

        return perimeter;
    }

    private double distance(Point p1, Point p2){

        double distance = (Math.sqrt(Math.pow(p1.getCoordinate_x()-p2.getCoordinate_x(),2)
                          + Math.pow(p1.getCoordinate_y()-p2.getCoordinate_y(),2)));
        return distance;
    }


    public boolean contains_sun (){
        Polygon triangle = new Polygon();
        triangle.addPoint((int)p1.getCoordinate_x(),(int)p1.getCoordinate_y());
        triangle.addPoint((int)p2.getCoordinate_x(),(int)p2.getCoordinate_y());
        triangle.addPoint((int)p3.getCoordinate_x(),(int)p3.getCoordinate_y());

        return triangle.contains(sun.getCoordinate_x(),sun.getCoordinate_y());
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

    public Planet getP1() {
        return p1;
    }

    public void setP1(Planet p1) {
        this.p1 = p1;
    }

    public Planet getP2() {
        return p2;
    }

    public void setP2(Planet p2) {
        this.p2 = p2;
    }

    public Planet getP3() {
        return p3;
    }

    public void setP3(Planet p3) {
        this.p3 = p3;
    }
}
