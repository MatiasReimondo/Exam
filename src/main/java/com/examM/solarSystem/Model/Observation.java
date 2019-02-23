package com.examM.solarSystem.Model;

import java.awt.*;

public class Observation {

    private Planet p1;
    private Planet p2;
    private Planet p3;

    private static Point sun = new Point(0,0);

    private static double ERROR_DISTANCE = 10;
    private static double ERROR_ANGLE= 0.5;
    private static double ANGLE_ALINE_ZERO = 0;
    private static double ANGLE_ALINE_PI = 180;


    public Observation(Planet p1, Planet p2, Planet p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public Observation (Observation observation){

        this.p1 = new Planet(observation.getP1());
        this.p2 = new Planet(observation.getP2());
        this.p3 = new Planet(observation.getP3());
    }

    //Se comprueba la alineacion con el sol
    //comprobando que el sol sea colineal con alguno de los 3 planetas
    //Si el sol es colineal por ejemplo con el planeta 1 y 2
    //DsolP1 + DP1P2 = DsolP2
    //Se toma un error de tolerancia
    public boolean alined_with_sun(){

        double dst_sun_p1 = distance(sun,p1);
        double dst_sun_p2 = distance(sun,p2);
        double dst_sun_p3 = distance(sun,p3);

        double dst_p1_p2 = distance(p1,p2);
        double dst_p2_p3 = distance(p2,p3);
        double dst_p1_p3 = distance(p1,p3);

        double coef1 =Math.abs(dst_sun_p2 -(dst_sun_p1+dst_p1_p2));
        double coef2 = Math.abs(dst_sun_p3 -(dst_sun_p2+dst_p2_p3));
        double coef3 = Math.abs(dst_sun_p3 -(dst_sun_p1+dst_p1_p3));

        if(coef1 < ERROR_DISTANCE || coef2< ERROR_DISTANCE || coef3 < ERROR_DISTANCE){
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

    //Distancia entre dos puntos
    private double distance(Point p1, Point p2){

        double distance = (Math.sqrt(Math.pow(p1.getCoordinate_x()-p2.getCoordinate_x(),2)
                          + Math.pow(p1.getCoordinate_y()-p2.getCoordinate_y(),2)));
        return distance;
    }

    //Creamos un poligono y usamos el metodo para ver si contiene el sol
    //Se pierde precision ya que el metodo soporta solamene enteros
    //Futura mejora
    public boolean contains_sun (){
        Polygon triangle = new Polygon();
        triangle.addPoint((int)p1.getCoordinate_x(),(int)p1.getCoordinate_y());
        triangle.addPoint((int)p2.getCoordinate_x(),(int)p2.getCoordinate_y());
        triangle.addPoint((int)p3.getCoordinate_x(),(int)p3.getCoordinate_y());

        return triangle.contains(sun.getCoordinate_x(),sun.getCoordinate_y());
    }

    //Tomamos como segmento P2->P1 (V->F)
    //Y P2->P3 (V->B)
    //Valor del angulo entre -180 a 180
    public double angleBetweenSegments(){

        Point pi = new Point(p1.getCoordinate_x()-p2.getCoordinate_x(),p1.getCoordinate_y()-p2.getCoordinate_y());
        Point pj = new Point(p3.getCoordinate_x()-p2.getCoordinate_x(),p3.getCoordinate_y()-p2.getCoordinate_y());

        double angle_pi=Math.toDegrees(Math.atan2(pi.getCoordinate_y(),pi.getCoordinate_x()));
        double angle_pj=Math.toDegrees(Math.atan2(pj.getCoordinate_y(),pj.getCoordinate_x()));

        double angle=angle_pi-angle_pj;
        if(angle >180){
            angle -=360;
        }
        if(angle < -180){
            angle+=360;
        }

        return angle;
    }

    //La alineacion se da cuando el valor absoluto del angulo
    // es cercano a 180 o a 0
    //Se toma un error de tolerancia
    public boolean check_aline(){
        double abs_angle = Math.abs(this.angleBetweenSegments());

        boolean aline_0 = (abs_angle < ANGLE_ALINE_ZERO+ERROR_ANGLE);
        boolean aline_pi = (ANGLE_ALINE_PI-ERROR_ANGLE < abs_angle) ;

        return (aline_0 || aline_pi);
    }

    public void rewind_planets(int factor){
        this.p1.rewind(factor);
        this.p2.rewind(factor);
        this.p3.rewind(factor);
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
