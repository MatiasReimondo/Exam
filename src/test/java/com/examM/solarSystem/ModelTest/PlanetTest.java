package com.examM.solarSystem.ModelTest;

import com.examM.solarSystem.Model.Planet;
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
        planet1.roundError();
        planet2.roundError();
        planet3.roundError();
        Assert.assertTrue(planet1.getCoordinate_x() == 500);
        Assert.assertTrue(planet1.getCoordinate_y() == 0);
        Assert.assertTrue(planet2.getCoordinate_x() == 1000);
        Assert.assertTrue(planet2.getCoordinate_y() == 0);
        Assert.assertTrue(planet3.getCoordinate_x() == 2000);
        Assert.assertTrue(planet3.getCoordinate_y() == 0);

    }

    @Test
    public void planetRewind(){
        planet1.forward();
        planet2.forward();
        planet3.forward();
        planet1.rewind(1);
        planet2.rewind(1);
        planet3.rewind(1);

        planet1.roundError();
        planet2.roundError();
        planet3.roundError();

        Assert.assertTrue(planet1.getCoordinate_x() == 500);
        Assert.assertTrue(planet1.getCoordinate_y() == 0);
        Assert.assertTrue(planet2.getCoordinate_x() == 1000);
        Assert.assertTrue(planet2.getCoordinate_y() == 0);
        Assert.assertTrue(planet3.getCoordinate_x() == 2000);
        Assert.assertTrue(planet3.getCoordinate_y() == 0);

    }

    @Test
    public void planetQuarterRewind(){
        planet1.forward();
        planet1.rewind(4);
        planet1.rewind(4);
        planet1.rewind(4);
        planet1.rewind(4);

        //Cancelamos el error
        planet1.roundError();


        Assert.assertTrue(planet1.getCoordinate_x() == 500);
        Assert.assertTrue(planet1.getCoordinate_y() == 0);
    }

    @Test
    public void planet3Forward1Rewind(){
        planet3.forward();
        planet3.roundError();

        double x = planet3.getCoordinate_x();
        double y = planet3.getCoordinate_y();

        planet3.forward();
        planet3.roundError();

        planet3.rewind(1);
        planet3.roundError();

        Assert.assertTrue(planet3.getCoordinate_x() == x);
        Assert.assertTrue(planet3.getCoordinate_y() == y);

    }

    @Test
    public void planet3Forward3RewindFactor4(){
        planet3.forward();
        planet3.roundError();

        planet3.forward();
        planet3.roundError();

        double x = planet3.getCoordinate_x();
        double y = planet3.getCoordinate_y();

        planet3.forward();
        planet3.roundError();

        planet3.rewind(4);
        planet3.rewind(4);
        planet3.rewind(4);
        planet3.rewind(4);

        planet3.roundError();


        Assert.assertTrue(planet3.getCoordinate_x() == x);
        Assert.assertTrue(planet3.getCoordinate_y() == y);

    }

    @Test
    public void planet3Forward10RewindFactor10(){
        for (int i =0; i<10;i++){
            planet3.forward();
            planet3.roundError();
        }
        double x = planet3.getCoordinate_x();
        double y = planet3.getCoordinate_y();

        planet3.forward();
        planet3.roundError();

        for (int j = 0;j<10;j++){
            planet3.rewind(10);
        }
        planet3.roundError();


        Assert.assertTrue(planet3.getCoordinate_x() == x);
        Assert.assertTrue(planet3.getCoordinate_y() == y);

    }
}
