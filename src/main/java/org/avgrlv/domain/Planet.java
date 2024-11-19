package org.avgrlv.domain;

import java.util.ArrayList;
import java.util.List;

public class Planet extends SpaceObject {
    private List<Satellite> satellites = new ArrayList<>();

    public List<Satellite> getSatellites() {
        return satellites;
    }

    public void addSatellite(Satellite satellite) {
        this.satellites.add(satellite);
    }

    public Planet(String name, double mass, List<Satellite> satellites) {
        super(name, mass);
        this.satellites = satellites;
    }
}
