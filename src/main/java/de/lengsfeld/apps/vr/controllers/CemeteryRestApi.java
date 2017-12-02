package de.lengsfeld.apps.vr.controllers;

import de.lengsfeld.apps.vr.entity.Cemetery;
import de.lengsfeld.apps.vr.entity.Grave;
import de.lengsfeld.apps.vr.service.CemeteryService;
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
public class CemeteryRestApi {

    public static final Logger logger = LoggerFactory.getLogger(CemeteryRestApi.class);

    @Autowired
    CemeteryService cemeteryService; //Service which will do all data retrieval/manipulation work

    @Autowired
    GraveService graveService;

    // -------------------Retrieve All Cemeteries---------------------------------------------

    @RequestMapping(value = "/cemetery/", method = RequestMethod.GET)
    public ResponseEntity<List<Cemetery>> listAllCemeteries() {
        List<Cemetery> cemeteries = cemeteryService.findAllCemeteries();
        if (cemeteries.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Cemetery>>(cemeteries, HttpStatus.OK);
    }

    // -------------------Retrieve Single Cemetery------------------------------------------

    @RequestMapping(value = "/cemetery/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCemetery(@PathVariable("id") long id) {
        logger.info("Fetching Cemetery with id {}", id);
        Cemetery cemetery = cemeteryService.findById(id);
        if (cemetery == null) {
            logger.error("Cemetery with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Cemetery with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Cemetery>(cemetery, HttpStatus.OK);
    }

    // -------------------Retrieve Graves in Cemetery------------------------------------------

    @RequestMapping(value = "/cemetery/{id}/graves", method = RequestMethod.GET)
    public ResponseEntity<?> getGravesInCemetery(@PathVariable("id") long id) {
        logger.info("Fetching Cemetery with id {}", id);
        Cemetery cemetery = cemeteryService.findById(id);
        if (cemetery == null) {
            logger.error("Cemetery with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Cemetery with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        List<Grave> graves = graveService.findByCemetery(cemetery);
        return new ResponseEntity<List<Grave>>(graves, HttpStatus.OK);
    }

    // -------------------Create a Cemetery-------------------------------------------

    @RequestMapping(value = "/cemetery/", method = RequestMethod.POST)
    public ResponseEntity<?> createCemetery(@RequestBody Cemetery cemetery, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Cemetery : {}", cemetery);

        if (cemeteryService.isCemeteryExist(cemetery)) {
            logger.error("Unable to create. A Cemetery with name {} already exist", cemetery.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A Cemetery with name " +
                    cemetery.getName() + " already exist."), HttpStatus.CONFLICT);
        }
        cemeteryService.saveCemetery(cemetery);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/cemetery/{id}").buildAndExpand(cemetery.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    // ------------------- Update a Cemetery ------------------------------------------------

    @RequestMapping(value = "/cemetery/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCemetery(@PathVariable("id") long id, @RequestBody Cemetery cemetery) {
        logger.info("Updating Cemetery with id {}", id);

        Cemetery currentCemetery = cemeteryService.findById(id);

        if (currentCemetery == null) {
            logger.error("Unable to update. Cemetery with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. Cemetery with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentCemetery.setName(cemetery.getName());

        cemeteryService.updateCemetery(currentCemetery);
        return new ResponseEntity<Cemetery>(currentCemetery, HttpStatus.OK);
    }

    // ------------------- Delete a Cemetery-----------------------------------------

    @RequestMapping(value = "/cemetery/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCemetery(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting Cemetery with id {}", id);

        Cemetery cemetery = cemeteryService.findById(id);
        if (cemetery == null) {
            logger.error("Unable to delete. Cemetery with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Cemetery with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        cemeteryService.deleteCemeteryById(id);
        return new ResponseEntity<Cemetery>(HttpStatus.NO_CONTENT);
    }

    // ------------------- Delete All Cemeteries-----------------------------

    @RequestMapping(value = "/cemetery/", method = RequestMethod.DELETE)
    public ResponseEntity<Cemetery> deleteAllCemeteries() {
        logger.info("Deleting All Cemeteries");

        cemeteryService.deleteAllCemeteries();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}