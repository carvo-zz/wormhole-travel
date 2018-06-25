package com.gamesys.wormholetravel.models;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public void travelTo(Travel destination) {
        Optional.ofNullable(getCurrentTravel())
                .ifPresentOrElse(
                        ct -> {
                            addHistoric(ct);
                            setCurrentTravel(destination);
                        },
                        () -> setCurrentTravel(destination)
                );
    }

    private void addHistoric(Travel travel) {
        this.historic = Optional.ofNullable(this.historic).orElseGet(ArrayList::new);
        this.historic.add(travel);
    }

}
