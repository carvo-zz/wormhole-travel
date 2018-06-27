package com.gamesys.wormholetravel.repositories;

import com.gamesys.wormholetravel.models.Traveler;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

//@RepositoryRestResource(collectionResourceRel = "travelers", path = "traveler")
public interface TravelerRepository extends MongoRepository<Traveler, String> {
    Traveler findByPgi(@Param("pgi") String pgi);
}
