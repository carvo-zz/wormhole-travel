package com.gamesys.wormholetravel.services;

import com.gamesys.wormholetravel.commons.ServiceResponse;
import com.gamesys.wormholetravel.models.Travel;
import com.gamesys.wormholetravel.models.Traveler;
import com.gamesys.wormholetravel.repositories.TravelerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DefaultTravelerService implements TravelerService {

    @Autowired
    private TravelerRepository repository;

    @Override
    public ServiceResponse<List<Traveler>> findAll() {
        return new ServiceResponse<>(repository.findAll());
    }

    @Override
    public ServiceResponse<List<Travel>> findHistoric(final String pgi) {
        final Traveler traveler = repository.findByPgi(pgi);
        if (traveler == null) {
            return new ServiceResponse<>(Map.of("error.pgi", "PGI not found"));
        } else {
            return new ServiceResponse<>(traveler.getHistoric());
        }
    }

    @Override
    public ServiceResponse travel(final String pgi, final Travel travel) {
        Optional.ofNullable(repository.findByPgi(pgi))
                .ifPresentOrElse(
                        traveler -> update(traveler, travel),
                        () -> repository.save(new Traveler(pgi, travel))
                );

        return new ServiceResponse();
    }

    private void update(final Traveler traveler, final Travel newTravel) {
        final List<Travel> historical = Optional
                .ofNullable(traveler.getHistoric())
                .orElseGet(ArrayList::new);

        Optional.ofNullable(traveler.getCurrentTravel())
                .ifPresent(historical::add);

        traveler.setCurrentTravel(newTravel);
        traveler.setHistoric(historical);

        repository.save(traveler);
    }
}
