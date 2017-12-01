package de.lengsfeld.apps.vr.controllers;

import de.lengsfeld.apps.vr.entity.Cemetery;
import de.lengsfeld.apps.vr.repository.CemeteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/c")
public class CemeteryController {

    @Autowired
    private CemeteryRepository cemeteryRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Cemetery> getAllCemeteries() {
        return cemeteryRepository.findAll();
    }

}
