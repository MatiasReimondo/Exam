package com.examM.solarSystem.Controllers;


import com.examM.solarSystem.Model.PeriodPrediction;
import com.examM.solarSystem.Model.ResponsePrediction;
import com.examM.solarSystem.Model.Simulation;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;

@RestController
public class BasicController {

    public static Simulation simulation = new Simulation();

    @RequestMapping("/")
    public ResponsePrediction predictioResult(){
        return new ResponsePrediction(simulation.getCounter_rain(),simulation.getCounter_drought(),simulation.getCounter_optimal(), simulation.getDay_max_rain());
    }

    @RequestMapping(value ="/clima", method = RequestMethod.GET)
    public PeriodPrediction prediction(@RequestParam(value="day") long day){
        return simulation.getDays_in_cicle().get(day%360);

    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        simulation.simulateCicle();

    }
}
