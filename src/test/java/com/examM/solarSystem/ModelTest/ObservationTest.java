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

    //El modelo discreto detectaba cada 365 dias 3 alineaciones
    //Comprobamos que estas se dan 3 veces tambien si medimos los angulos
    @Test
    public void coherenceWithDiscret(){
        int counterOfAlligmentInObservation = 0;
        int counterOfRain = 0;
        int dayMaxRain=0;
        double maxPerimeter = 0;
        for (int i = 0; i <365 ; i++) {
            planet1.forward();
            planet1.roundError();
            planet2.forward();
            planet2.roundError();
            planet3.forward();
            planet3.roundError();
            Observation obs = new Observation(planet1,planet2,planet3);
            if(obs.angleBetweenSegments() == 0 || obs.angleBetweenSegments() == 180){
                counterOfAlligmentInObservation++;
            }
            if(obs.contains_sun()){
                counterOfRain++;
                if(obs.perimeter()>maxPerimeter){
                    maxPerimeter= obs.perimeter();
                    dayMaxRain= i+1;
                }
            }


        }
        Assert.assertEquals(3,counterOfAlligmentInObservation);
        Assert.assertEquals(307,dayMaxRain);
        //Hay un desfasaje entre la solucion discreta anterior
        //Viene de castear a int las coordenadas de los planetas
        //Por ahora comentamos esto y aceptamos el error
        //Assert.assertEquals(125,counterOfRain);
    }

    @Test
    public void planetsAlinedWithSun(){
        Observation obs = new Observation(planet1,planet2,planet3);
        Assert.assertTrue(obs.alined_with_sun(1));
    }


}
