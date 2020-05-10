package com.mattjohnson.teai3.model;

import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Car extends RepresentationModel {

    @NotNull
    @Min(1)
    private long id;

    @NotNull(message = "mark cannot be null")
    @Size(min = 2)
    private String mark;


    @NotNull(message = "model cannot be null")
    @Size(min = 2)
    private String model;

    @NotNull(message = "color cannot be null")
    private Color color;


    public Car(long id, String mark, String model, Color color) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
