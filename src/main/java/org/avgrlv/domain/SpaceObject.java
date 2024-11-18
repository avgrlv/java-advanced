package org.avgrlv.domain;

public abstract class SpaceObject {
    private String name;
    private double mass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public SpaceObject(String name, double mass) {
        this.name = name;
        this.mass = mass;
    }
}
