package com.examM.solarSystem.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name= "predictions")
public class Prediction implements Serializable {

    @Id
    private final long day;

    @NotBlank
    @Column(columnDefinition = "text")
    private final Weather detail;

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
