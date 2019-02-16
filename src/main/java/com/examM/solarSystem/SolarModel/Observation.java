package com.examM.solarSystem.SolarModel;

//Clase que se encargara de la logica de la observacion
// y su resultado
//Para esto planteamos que cada posicion de cada planeta
//es un vertice
public class Observation {

    private Point location_p1;
    private Point location_p2;
    private Point location_p3;

    private static Point location_s = new Point(0,0);

    private double triangle_area;

    private double triangle_perimeter;

    private boolean alignment;

    public Observation(Point location_p1, Point location_p2, Point location_p3) {
        this.location_p1 = location_p1;
        this.location_p2 = location_p2;
        this.location_p3 = location_p3;
        this.triangle_area = this.calculate_area(location_p1,location_p2,location_p3);
        this.alignment = (this.triangle_area == 0);
        this.triangle_perimeter = this.calculate_perimeter(location_p1,location_p2,location_p3);
    }


    //http://www.mathopenref.com/coordtrianglearea.html
    /*
    Given the coordinates of the three vertices of any triangle, the area of the triangle is given by:
    area = Ax(By−Cy)+Bx(Cy−Ay)+Cx(Ay−By)/2
    where Ax and Ay are the x and y coordinates of the point A etc..
     */
    private double calculate_area(Point p1,Point p2, Point p3){
        double calc_area = (p1.getCoordinate_x() * (p2.getCoordinate_y()-p3.getCoordinate_y())
                + p2.getCoordinate_x()*(p3.getCoordinate_y()-p1.getCoordinate_y())
                + p3.getCoordinate_x()*(p1.getCoordinate_y()-p2.getCoordinate_y()))/2;
        return calc_area;
    }

    private double calculate_perimeter(Point p1, Point p2, Point p3){
        double distance1 = (Math.sqrt(Math.pow(p1.getCoordinate_x()-p2.getCoordinate_x(),2)
                                      +Math.pow(p1.getCoordinate_y()-p2.getCoordinate_y(),2)));

        double distance2 = (Math.sqrt(Math.pow(p2.getCoordinate_x()-p3.getCoordinate_x(),2)
                                      +Math.pow(p2.getCoordinate_y()-p3.getCoordinate_y(),2)));

        double distance3 = (Math.sqrt(Math.pow(p3.getCoordinate_x()-p1.getCoordinate_x(),2)
                                      +Math.pow(p3.getCoordinate_y()-p1.getCoordinate_y(),2)));

        double calc_perimeter = distance1 + distance2 + distance3;

        return calc_perimeter;
    }

    //Si los puntos estan alineados solo basta con comprobar que el triangulo
    //Formado por dos puntos  y el sol tiene area 0
    //Elijo p1 y p2 de manera random
    private boolean solar_alignment(){
        return (calculate_area(location_s,location_p1,location_p2)== 0);
    }

    //https://stackoverflow.com/questions/2049582/how-to-determine-if-a-point-is-in-a-2d-triangle
    //Posicion del sol (0,0)
    //Como solo nos interesa el sol dejamos a este punto fijo
    private double sign(Point p1, Point p2) {
        return (location_s.getCoordinate_x() - p2.getCoordinate_x()) * (p1.getCoordinate_y() - p2.getCoordinate_y())
                -(p1.getCoordinate_x() - p2.getCoordinate_x()) * (location_s.getCoordinate_y() - p2.getCoordinate_y());
    }


    public boolean contains_sun (){
        double d1, d2, d3;
        boolean has_neg, has_pos;

        d1 = sign(this.location_p1,this.location_p2);
        d2 = sign(this.location_p2,this.location_p3);
        d3 = sign(this.location_p3,this.location_p1);

        has_neg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        has_pos = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(has_neg && has_pos);
    }

    public boolean are_planets_alinged(){
        return this.alignment;
    }

    public boolean is_sun_alinged(){
        if(this.alignment){
            return contains_sun();
        }else {
            return false;
        }
    }

    public double getTriangle_area() {
        return triangle_area;
    }
}
