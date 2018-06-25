package com.gamesys.wormholetravel.services;

import com.gamesys.wormholetravel.commons.ServiceResponse;
import com.gamesys.wormholetravel.models.Travel;
import com.gamesys.wormholetravel.models.Traveler;
import com.gamesys.wormholetravel.repositories.TravelerRepository;
import com.gamesys.wormholetravel.validators.TravelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ServiceResponse travel(final String pgi, final Travel destination) {
        return Optional.ofNullable(repository.findByPgi(pgi))
                .map(t -> update(t, destination))
                .orElseGet(() -> {
                    this.save(new Traveler(pgi, destination));
                    return new ServiceResponse();
                });
    }

    private Traveler save(final Traveler traveler) {
        return repository.save(traveler);
    }

    private ServiceResponse update(final Traveler traveler, final Travel destination) {
        final Map<String, String> errors = validator.validateTravel(traveler.getCurrentTravel(), destination);

        return Optional.ofNullable(errors)
                .filter(err -> !err.isEmpty())
                .map(ServiceResponse::new)
                .orElseGet(() -> {
                    traveler.travelTo(destination);
                    this.save(traveler);

                    return new ServiceResponse();
                });
    }


}
