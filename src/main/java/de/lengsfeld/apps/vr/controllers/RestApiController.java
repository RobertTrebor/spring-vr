package de.lengsfeld.apps.vr.controllers;

import de.lengsfeld.apps.vr.entity.Grave;
import de.lengsfeld.apps.vr.service.GraveService;
import de.lengsfeld.apps.vr.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiController {

    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    GraveService graveService; //Service which will do all data retrieval/manipulation work

    // -------------------Retrieve All Graves---------------------------------------------

    @RequestMapping(value = "/grave/", method = RequestMethod.GET)
    public ResponseEntity<List<Grave>> listAllGraves() {
        List<Grave> graves = graveService.findAllGraves();
        if (graves.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Grave>>(graves, HttpStatus.OK);
    }

    // -------------------Retrieve Single Grave------------------------------------------

    @RequestMapping(value = "/grave/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getGrave(@PathVariable("id") long id) {
        logger.info("Fetching Grave with id {}", id);
        Grave grave = graveService.findById(id);
        if (grave == null) {
            logger.error("Grave with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Grave with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Grave>(grave, HttpStatus.OK);
    }

    // -------------------Create a Grave-------------------------------------------

    @RequestMapping(value = "/grave/", method = RequestMethod.POST)
    public ResponseEntity<?> createGrave(@RequestBody Grave grave, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Grave : {}", grave);

        if (graveService.isGraveExist(grave)) {
            logger.error("Unable to create. A Grave with name {} already exist", grave.getFirstName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A Grave with name " +
                    grave.getFirstName() + " already exist."), HttpStatus.CONFLICT);
        }
        graveService.saveGrave(grave);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/grave/{id}").buildAndExpand(grave.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    // ------------------- Update a Grave ------------------------------------------------

    @RequestMapping(value = "/grave/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateGrave(@PathVariable("id") long id, @RequestBody Grave grave) {
        logger.info("Updating Grave with id {}", id);

        Grave currentGrave = graveService.findById(id);

        if (currentGrave == null) {
            logger.error("Unable to update. Grave with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. Grave with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentGrave.setFirstName(grave.getFirstName());

        graveService.updateGrave(currentGrave);
        return new ResponseEntity<Grave>(currentGrave, HttpStatus.OK);
    }

    // ------------------- Delete a Grave-----------------------------------------

    @RequestMapping(value = "/grave/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteGrave(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting Grave with id {}", id);

        Grave grave = graveService.findById(id);
        if (grave == null) {
            logger.error("Unable to delete. Grave with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Grave with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        graveService.deleteGraveById(id);
        return new ResponseEntity<Grave>(HttpStatus.NO_CONTENT);
    }

    // ------------------- Delete All Graves-----------------------------

    @RequestMapping(value = "/grave/", method = RequestMethod.DELETE)
    public ResponseEntity<Grave> deleteAllGraves() {
        logger.info("Deleting All Graves");

        graveService.deleteAllGraves();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}