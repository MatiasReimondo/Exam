package com.examM.solarSystem.ModelTest;

import com.examM.solarSystem.Model.Observation;
import com.examM.solarSystem.Model.Planet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ObservationTest {

    Planet planet1;
    Planet planet2;
    Planet planet3;


    @Before
    public void setUp(){
        planet1 = new Planet("Planet1",500,-1);
        planet2 = new Planet("Planet2",1000,5);
        planet3 = new Planet("Planet3",2000,-3);

    }

    @Test
    public void planetsAlinedWithSun(){
        Observation obs = new Observation(planet1,planet2,planet3);
        Assert.assertTrue(obs.alined_with_sun(1));
    }

    @Test
    public void alinedAt90(){
        for (int i = 0; i <90 ; i++) {
            planet1.forward();
            planet2.forward();
            planet3.forward();

        }
        Observation obs = new Observation(planet1,planet2,planet3);
        Assert.assertTrue(obs.check_aline());
        Assert.assertTrue(obs.alined_with_sun(1));
    }

    @Test
    public void alinedAt180(){
        for (int i = 0; i <180 ; i++) {
            planet1.forward();
            planet2.forward();
            planet3.forward();

        }
        Observation obs = new Observation(planet1,planet2,planet3);
        Assert.assertTrue(obs.check_aline());
        Assert.assertTrue(obs.alined_with_sun(1));
    }

    @Test
    public void alinedAt270(){
        for (int i = 0; i <270 ; i++) {
            planet1.forward();
            planet2.forward();
            planet3.forward();

        }
        Observation obs = new Observation(planet1,planet2,planet3);
        Assert.assertTrue(obs.check_aline());
        Assert.assertTrue(obs.alined_with_sun(1));
    }

    @Test
    public void angleBetweenSeg90(){
        planet1.setCoordinate_x(500);
        planet1.setCoordinate_y(0);

        planet2.setCoordinate_x(1000);
        planet2.setCoordinate_y(0);

        planet3.setCoordinate_x(1000);
        planet3.setCoordinate_y(2000);

        Observation obs = new Observation(planet1,planet2,planet3);

        Assert.assertEquals(90,obs.angleBetweenSegments(),0.01 );
    }

    @Test
    public void angleBetweenSegMinus90(){
        planet1.setCoordinate_x(500);
        planet1.setCoordinate_y(0);

        planet2.setCoordinate_x(1000);
        planet2.setCoordinate_y(0);

        planet3.setCoordinate_x(1000);
        planet3.setCoordinate_y(-2000);

        Observation obs = new Observation(planet1,planet2,planet3);

        Assert.assertEquals(-90,obs.angleBetweenSegments(),0.01 );
    }

    @Test
    public void angleBetweenSeg0(){
        planet1.setCoordinate_x(500);
        planet1.setCoordinate_y(0);

        planet2.setCoordinate_x(1000);
        planet2.setCoordinate_y(0);

        planet3.setCoordinate_x(-2000);
        planet3.setCoordinate_y(0);

        Observation obs = new Observation(planet1,planet2,planet3);

        Assert.assertEquals(0,obs.angleBetweenSegments(),0.01 );
    }

    @Test
    public void angleBetweenSeg360(){
        planet1.setCoordinate_x(500);
        planet1.setCoordinate_y(0);

        planet2.setCoordinate_x(1000);
        planet2.setCoordinate_y(0);

        planet3.setCoordinate_x(-2000);
        planet3.setCoordinate_y(-10);

        Observation obs = new Observation(planet1,planet2,planet3);

        Assert.assertEquals(0,obs.angleBetweenSegments(),1 );
    }

    @Test
    public void angleBetweenMinusPiPi(){
        double angleMax =-1000;
        double angleMin = 1000;
        for (int i = 0; i <360 ; i++) {
            planet1.forward();
            planet2.forward();
            planet3.forward();
            Observation obs = new Observation(planet1,planet2,planet3);
            double obsAngle = obs.angleBetweenSegments();
            if(angleMax<obsAngle){
                angleMax = obsAngle;
            }
            if(angleMin> obsAngle){
                angleMin = obsAngle;
            }

        }
        Assert.assertEquals(180,angleMax,0.1 );
        Assert.assertEquals(-180,angleMin,0.1 );

    }

    @Test
    public void alinedAllRight (){
        Observation obs = new Observation(planet1,planet2,planet3);
        Assert.assertTrue(obs.alined_with_sun(1));
    }

    @Test
    public void alinedTwoRightOneLeft(){
        planet1.setCoordinate_x(-500);
        Observation obs = new Observation(planet1,planet2,planet3);
        Assert.assertTrue(obs.alined_with_sun(1));
    }

    @Test
    public void alinedTwoLeft (){
        planet1.setCoordinate_x(-500);
        planet2.setCoordinate_x(-1000);
        Observation obs = new Observation(planet1,planet2,planet3);
        Assert.assertTrue(obs.alined_with_sun(1));
    }

    @Test
    public void alinedAllLeft (){
        planet1.setCoordinate_x(-500);
        planet2.setCoordinate_x(-1000);
        planet3.setCoordinate_x(-2000);
        Observation obs = new Observation(planet1,planet2,planet3);
        Assert.assertTrue(obs.alined_with_sun(1));
    }

    @Test
    public void alinedP1RightP2P3Left (){
        planet2.setCoordinate_x(-1000);
        planet3.setCoordinate_x(-2000);
        Observation obs = new Observation(planet1,planet2,planet3);
        Assert.assertTrue(obs.alined_with_sun(1));
    }

    @Test
    public void alinedP2RightP1P3Left (){
        planet1.setCoordinate_x(-500);
        planet3.setCoordinate_x(-2000);
        Observation obs = new Observation(planet1,planet2,planet3);
        Assert.assertTrue(obs.alined_with_sun(1));
    }

    @Test
    public void alinedP3RightP1P2Left (){
        planet3.setCoordinate_x(-2000);
        Observation obs = new Observation(planet1,planet2,planet3);
        Assert.assertTrue(obs.alined_with_sun(1));
    }

    @Test
    public void alinedP2LeftP1P3Right (){
        planet2.setCoordinate_x(-1000);
        Observation obs = new Observation(planet1,planet2,planet3);
        Assert.assertTrue(obs.alined_with_sun(1));
    }




}
