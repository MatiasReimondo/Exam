package com.examM.solarSystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name= "predictions")
public class Prediction implements Serializable{

    @Id
    private  long day;

    @Column(columnDefinition = "text")
    private  Weather detail;

    public Prediction() {
    }

    public Prediction(long day, Weather detail){
        this.day= day;
        this.detail = detail;
    }


    public long getDay() {
        return day;
    }

    public Weather getDetail() {
        return detail;
    }
}
