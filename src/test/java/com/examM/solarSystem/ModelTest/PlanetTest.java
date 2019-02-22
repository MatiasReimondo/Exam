package com.examM.solarSystem.ModelTest;

import com.examM.solarSystem.Model.Planet;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlanetTest {

    Planet planet1;
    Planet planet2;
    Planet planet3;

    @Before
    public void setUp(){
        planet1 = new Planet("Planet1",500,-1);
        planet2 = new Planet("Planet2",1000,5);
        planet3 = new Planet("Planet3",2000,-3);
    }

    //Si se mueven todos los planetas 360 veces (dias) deberian estar todos en la misma posicion de origen
    @Test
    public void planetRotationAroundTheSun(){
        for (int i=0; i<360;i++){
            planet1.forward();
            planet2.forward();
            planet3.forward();
        }
        Assert.assertEquals(500,planet1.getCoordinate_x(),0.1);
        Assert.assertEquals(0,planet1.getCoordinate_y(),0.1);
        Assert.assertEquals(1000,planet2.getCoordinate_x(),0.1);
        Assert.assertEquals(0,planet2.getCoordinate_y(),0.1);
        Assert.assertEquals(2000,planet3.getCoordinate_x(),0.1);
        Assert.assertEquals(0,planet3.getCoordinate_y(),0.1);
    }

    @Test
    public void planetRewind(){
        planet1.forward();
        planet2.forward();
        planet3.forward();
        planet1.rewind(1);
        planet2.rewind(1);
        planet3.rewind(1);

        Assert.assertEquals(500,planet1.getCoordinate_x(),0.1);
        Assert.assertEquals(0,planet1.getCoordinate_y(),0.1);
        Assert.assertEquals(1000,planet2.getCoordinate_x(),0.1);
        Assert.assertEquals(0,planet2.getCoordinate_y(),0.1);
        Assert.assertEquals(2000,planet3.getCoordinate_x(),0.1);
        Assert.assertEquals(0,planet3.getCoordinate_y(),0.1);

    }

    @Test
    public void planetQuarterRewind(){
        planet1.forward();
        planet1.rewind(4);
        planet1.rewind(4);
        planet1.rewind(4);
        planet1.rewind(4);

        Assert.assertEquals(500,planet1.getCoordinate_x(),0.1);
        Assert.assertEquals(0,planet1.getCoordinate_y(),0.1);
    }

    @Test
    public void planet3Forward1Rewind(){
        planet3.forward();

        double x = planet3.getCoordinate_x();
        double y = planet3.getCoordinate_y();

        planet3.forward();

        planet3.rewind(1);

        Assert.assertTrue(planet3.getCoordinate_x() == x);
        Assert.assertTrue(planet3.getCoordinate_y() == y);

    }

    @Test
    public void planet3Forward3RewindFactor4(){
        planet3.forward();

        planet3.forward();

        double x = planet3.getCoordinate_x();
        double y = planet3.getCoordinate_y();

        planet3.forward();

        planet3.rewind(4);
        planet3.rewind(4);
        planet3.rewind(4);
        planet3.rewind(4);


        Assert.assertTrue(planet3.getCoordinate_x() == x);
        Assert.assertTrue(planet3.getCoordinate_y() == y);

    }

    @Test
    public void planet3Forward10RewindFactor10(){
        for (int i =0; i<10;i++){
            planet3.forward();
        }
        double x = planet3.getCoordinate_x();
        double y = planet3.getCoordinate_y();

        planet3.forward();

        for (int j = 0;j<10;j++){
            planet3.rewind(10);
        }

        Assert.assertEquals(x,planet3.getCoordinate_x(),0.1);
        Assert.assertEquals(y,planet3.getCoordinate_y(),0.1);

    }

    @Test
    public void halfRewind135(){
        for (int i = 0; i <135; i++) {
            planet1.forward();

        }
        double pastx1 = planet1.getCoordinate_x();
        double pasty1 = planet1.getCoordinate_y();
        double pastAngle = planet1.getAngle();

        planet1.forward();
        double futureAngle = planet1.getAngle();
        planet1.rewind(2);

        double midleAngle = planet1.getAngle();
        planet1.rewind(2);

        Assert.assertTrue(futureAngle-planet1.getAngular_velocity()/2 == midleAngle);
        Assert.assertTrue(pastAngle==planet1.getAngle());

        Assert.assertTrue(pastx1 == planet1.getCoordinate_x());
        Assert.assertTrue(pasty1 == planet1.getCoordinate_y());
    }

    @Test
    public void QuarterRewind180(){
        for (int i = 0; i <180; i++) {
            planet1.forward();
        }
        double pastx1 = planet1.getCoordinate_x();
        double pasty1 = planet1.getCoordinate_y();
        double pastAngle = planet1.getAngle();

        planet1.forward();
        double futureAngle = planet1.getAngle();
        planet1.rewind(4);
        double q1Angle = planet1.getAngle();

        planet1.rewind(4);
        double q2Angle = planet1.getAngle();

        planet1.rewind(4);
        double q3Angle = planet1.getAngle();

        planet1.rewind(4);


        Assert.assertTrue(futureAngle-planet1.getAngular_velocity()/4 == q1Angle);
        Assert.assertTrue(futureAngle-planet1.getAngular_velocity()/2 == q2Angle);
        Assert.assertTrue(futureAngle-(3*planet1.getAngular_velocity()/4 )== q3Angle);

        Assert.assertTrue(pastAngle==planet1.getAngle());

        Assert.assertTrue(pastx1 == planet1.getCoordinate_x());
        Assert.assertTrue(pasty1 == planet1.getCoordinate_y());
    }
}
