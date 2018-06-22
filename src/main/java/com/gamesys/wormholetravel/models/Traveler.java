package com.gamesys.wormholetravel.models;

import java.util.List;

public class Traveler {
    private String personalGalacticIdentifier;
    private TravelDetail travel;
    private List<TravelDetail> historical;

    public String getPersonalGalacticIdentifier() {
        return personalGalacticIdentifier;
    }

    public void setPersonalGalacticIdentifier(String personalGalacticIdentifier) {
        this.personalGalacticIdentifier = personalGalacticIdentifier;
    }

    public TravelDetail getTravel() {
        return travel;
    }

    public void setTravel(TravelDetail travel) {
        this.travel = travel;
    }

    public List<TravelDetail> getHistorical() {
        return historical;
    }

    public void setHistorical(List<TravelDetail> historical) {
        this.historical = historical;
    }
}
