package com.examM.solarSystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Clase que posee la informacion del clima de cada dia
 * Persistida en la base de datos
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name= "predictions")
public class Prediction implements Serializable{

    @Id
    private  long day;

    @Column(columnDefinition = "text")
    private  Weather detail;

    /**
     * Constructor vacio
     * Requerido por Hibernate
     */
    public Prediction() {
    }

    /**
     * Constructor de la entidad
     * @param day
     * @param detail
     */
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
