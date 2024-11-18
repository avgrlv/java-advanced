package org.avgrlv.domain;

public class Satellite extends SpaceObject {
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Satellite(String name, double mass) {
        super(name, mass);
    }
}
