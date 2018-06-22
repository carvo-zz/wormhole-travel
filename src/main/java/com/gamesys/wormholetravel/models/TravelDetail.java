package com.gamesys.wormholetravel.models;

public class TravelDetail {
    private String place;
    private Long date;

    public TravelDetail(){
    }

    public TravelDetail(String place, Long date) {
        this.place = place;
        this.date = date;
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
