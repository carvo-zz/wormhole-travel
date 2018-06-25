package com.gamesys.wormholetravel.services;

import com.gamesys.wormholetravel.commons.DefaultServiceResponse;
import com.gamesys.wormholetravel.commons.ServiceResponse;
import com.gamesys.wormholetravel.models.Travel;
import com.gamesys.wormholetravel.models.Traveler;
import com.gamesys.wormholetravel.repositories.TravelerRepository;
import com.gamesys.wormholetravel.validators.TravelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DefaultTravelerService implements TravelerService {

    @Autowired
    private TravelValidator validator;

    @Autowired
    private TravelerRepository repository;

    @Override
    public ServiceResponse<List<Traveler>> findAll() {
        return new DefaultServiceResponse<>(repository.findAll());
    }

    @Override
    public ServiceResponse<List<Travel>> findHistoric(final String pgi) {
        return Optional.ofNullable(repository.findByPgi(pgi))
                .map(t -> new DefaultServiceResponse<>(t.getHistoric()))
                .orElse(new DefaultServiceResponse(new HashMap(){{ put("error.pgi", "PGI not found"); }}))
        ;
    }

    @Override
    public ServiceResponse<?> travel(final String pgi, final Travel destination) {
        return Optional.ofNullable(repository.findByPgi(pgi))
                .map(t -> update(t, destination))
                .orElseGet(() -> {
                    this.save(new Traveler(pgi, destination));
                    return new DefaultServiceResponse<>();
                });
    }

    private Traveler save(final Traveler traveler) {
        return repository.save(traveler);
    }

    private ServiceResponse<?> update(final Traveler traveler, final Travel destination) {
        final Map<String, String> errors = validator.validateTravel(traveler.getCurrentTravel(), destination);

        return Optional.ofNullable(errors)
                .filter(err -> !err.isEmpty())
                .map(err -> new DefaultServiceResponse<>(err))
                .orElseGet(() -> {
                    traveler.travelTo(destination);
                    this.save(traveler);

                    return new DefaultServiceResponse<>();
                });
    }


}
