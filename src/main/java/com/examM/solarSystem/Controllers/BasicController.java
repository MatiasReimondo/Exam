package com.examM.solarSystem.Controllers;


import com.examM.solarSystem.SolarModel.Simulation;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    public static Simulation simulation = new Simulation();

    @RequestMapping("/")
    public String hello(){
        String message1 = "La cantidad de dias con sequia en 10 años son: " + simulation.getCounter_drought().toString() + "\n";
        String message2 = "La cantidad de dias con condiciones optimas en 10 años son: "+ simulation.getCounter_optimal().toString() +"\n";
        String message3 = "La cantidad de dias con lluvia en 10 años son: "+ simulation.getCounter_rain().toString() +"\n";
        String finalMessage = message1+message2+message3;
        return finalMessage;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        simulation.simulate_x_days(365);

    }
}
