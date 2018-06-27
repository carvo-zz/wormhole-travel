package com.gamesys.wormholetravel.handlers;

import com.gamesys.wormholetravel.app.UrlMapping;
import com.gamesys.wormholetravel.commons.ServiceResponse;
import com.gamesys.wormholetravel.models.Travel;
import com.gamesys.wormholetravel.models.Traveler;
import com.gamesys.wormholetravel.services.TravelerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class TravelerHandler {

    @Autowired
    private TravelerService service;

    @ApiOperation(value = "${TravelerHandler.all.desc}", notes = "${TravelerHandler.all.notes}")
    @GetMapping(UrlMapping.Travelers.BASE)
    @ResponseStatus(HttpStatus.OK)
    public List<Traveler> all() {
        return service.findAll().getResponse();
    }

    @ApiOperation(value = "${TravelerHandler.historic.desc}", notes = "${TravelerHandler.historic.notes}")
    @ApiResponses({@ApiResponse(code = 200, message = "Successful retrieval of historic",
            response = Travel.class, responseContainer = "List")})
    @GetMapping(value = UrlMapping.Travelers.OLD_TRAVELS)
    public ResponseEntity<?> historic(@PathVariable String pgi) {
        final ServiceResponse response = service.findHistoric(pgi);

        if (response.hasError()) {
            return new ResponseEntity<>(response.getErrorsAsJson(), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(
                    Optional.ofNullable(response.getResponse()).orElseGet(Collections::emptyList),
                    HttpStatus.OK);
        }
    }

    @ApiOperation(value = "${TravelerHandler.postTravels.desc}", notes = "${TravelerHandler.postTravels.notes}")
    @PostMapping(UrlMapping.Travelers.TRAVELS)
    public ResponseEntity<String> postTravels(@ApiParam("${TravelerHandler.param.pgi}") @PathVariable String pgi,
                                              @RequestBody Travel destination) {
        return Optional.ofNullable(service.travel(pgi, destination))
                .filter(ServiceResponse::hasError)
                .map(ServiceResponse::getErrorsAsJson)
                .map(s -> new ResponseEntity(s, HttpStatus.BAD_REQUEST))
                .orElse(new ResponseEntity<>("", HttpStatus.CREATED))
                ;
    }


}
