package com.gamesys.wormholetravel.models;

import java.util.List;

public class Traveler {
    private String pgi;
    private TravelDetail currentTravel;
    private List<TravelDetail> historical;

    public String getPgi() {
        return pgi;
    }

    public void setPgi(String pgi) {
        this.pgi = pgi;
    }

    public TravelDetail getCurrentTravel() {
        return currentTravel;
    }

    public void setCurrentTravel(TravelDetail currentTravel) {
        this.currentTravel = currentTravel;
    }

    public List<TravelDetail> getHistorical() {
        return historical;
    }

    public void setHistorical(List<TravelDetail> historical) {
        this.historical = historical;
    }
}
