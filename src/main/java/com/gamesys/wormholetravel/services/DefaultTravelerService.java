package com.gamesys.wormholetravel.services;

import com.gamesys.wormholetravel.commons.DefaultServiceResponse;
import com.gamesys.wormholetravel.commons.ServiceResponse;
import com.gamesys.wormholetravel.factories.DefaultServiceResponseFactory;
import com.gamesys.wormholetravel.models.Travel;
import com.gamesys.wormholetravel.models.Traveler;
import com.gamesys.wormholetravel.repositories.TravelerRepository;
import com.gamesys.wormholetravel.validators.TravelValidator;
import com.gamesys.wormholetravel.validators.TravelerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DefaultTravelerService implements TravelerService {

    @Autowired
    private TravelValidator travelValidator;

    @Autowired
    private TravelerValidator travelerValidator;

    @Autowired
    private TravelerRepository repository;

    @Autowired
    private DefaultServiceResponseFactory responseFactory;

    @Override
    public ServiceResponse<List<Traveler>> findAll() {
        return new DefaultServiceResponse<>(repository.findAll());
    }

    @Override
    public ServiceResponse<List<Travel>> findHistoric(final String pgi) {
        return Optional.ofNullable(repository.findByPgi(pgi))
                .map(t -> responseFactory.createWithResponse(t.getHistoric()))
                .orElse(responseFactory.createWithError(
                        TravelerValidator.MSG.PgiNotFound.KEY,
                        TravelerValidator.MSG.PgiNotFound.MSG
                ))
                ;
    }

    @Override
    public ServiceResponse<Traveler> travel(final String pgi, final Travel destination) {
        return Optional.ofNullable(travelerValidator.validatePgi(pgi))
                .filter(err -> !err.isEmpty())
                .map(err -> responseFactory.createWithErrors(err))
                .orElseGet(() -> proceedToTravel(pgi, destination));
    }

    private ServiceResponse<Traveler> proceedToTravel(final String pgi, final Travel destination) {
        return Optional.ofNullable(repository.findByPgi(pgi))
                .map(t -> update(t, destination))
                .orElseGet(() -> insert(pgi, destination));
    }

    private ServiceResponse<Traveler> update(final Traveler traveler, final Travel destination) {
        final Map<String, String> errors = travelValidator.validateTravel(traveler.getCurrentTravel(), destination);

        return Optional.ofNullable(errors)
                .filter(err -> !err.isEmpty())
                .map(err -> responseFactory.createWithErrors(err))
                .orElseGet(() -> {
                    traveler.travelTo(destination);
                    this.save(traveler);

                    return responseFactory.createBlank();
                });
    }

    private ServiceResponse<Traveler> insert(String pgi, Travel destination) {
        final Map<String, String> errors = travelValidator.validateRequired(destination);

        return Optional.ofNullable(errors)
                .filter(err -> !err.isEmpty())
                .map(err -> responseFactory.createWithErrors(err))
                .orElseGet(() -> {
                    this.save(new Traveler(pgi, destination));
                    return responseFactory.createBlank();
                });
    }

    private Traveler save(final Traveler traveler) {
        return repository.save(traveler);
    }

}
