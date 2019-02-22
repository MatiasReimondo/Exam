package com.examM.solarSystem.Controllers;


import com.examM.solarSystem.Model.Prediction;
import com.examM.solarSystem.Model.ResponsePrediction;
import com.examM.solarSystem.Model.Simulation;
import com.examM.solarSystem.Repository.PredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;

@RestController
public class BasicController {

    @Autowired
    private PredictionRepository predictionRepository;

    private static Simulation simulation = new Simulation();

    @RequestMapping("/")
    public ResponsePrediction predictioResult(){
        return new ResponsePrediction(simulation.getCounter_rain(),simulation.getCounter_drought(),simulation.getCounter_optimal(), simulation.getDay_max_rain());
    }

    @RequestMapping(value ="/clima", method = RequestMethod.GET)
    public Prediction prediction(@RequestParam(value="day") long day){
        return predictionRepository.getOne(day);

    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        predictionRepository.saveAll(simulation.simulateDays());


    }
}
