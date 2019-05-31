package de.lengsfeld.apps.vr.controllers;

import de.lengsfeld.apps.vr.dto.GraveDTO;
import de.lengsfeld.apps.vr.service.GraveServiceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gravesapi")
public class GraveApiController {

    public static final Logger logger = LoggerFactory.getLogger(GraveApiController.class);

    @Autowired
    private GraveServiceDTO graveService; //Service which will do all data retrieval/manipulation work

    // -------------------Retrieve All Graves---------------------------------------------

    @GetMapping
    public List<GraveDTO> findAll() {
        return graveService.findAll();
    }


}
