package com.gamesys.wormholetravel.models;

public class TravelDetail {
    private String personalGalacticIdentifier;
    private String place;
    private Long date;


    public String getPersonalGalacticIdentifier() {
        return personalGalacticIdentifier;
    }

    public void setPersonalGalacticIdentifier(String personalGalacticIdentifier) {
        this.personalGalacticIdentifier = personalGalacticIdentifier;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
