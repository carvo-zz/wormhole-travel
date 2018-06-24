package com.gamesys.wormholetravel.models;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Traveler {
    @Id
    private String pgi;
    private Travel currentTravel;
    private List<Travel> historic;

    public Traveler() {

    }

    public Traveler(String pgi, Travel currentTravel) {
        this.pgi = pgi;
        this.currentTravel = currentTravel;
    }

    public String getPgi() {
        return pgi;
    }

    public void setPgi(String pgi) {
        this.pgi = pgi;
    }

    public Travel getCurrentTravel() {
        return currentTravel;
    }

    public void setCurrentTravel(Travel currentTravel) {
        this.currentTravel = currentTravel;
    }

    public List<Travel> getHistoric() {
        return historic;
    }

    public void setHistoric(List<Travel> historic) {
        this.historic = historic;
    }
}
