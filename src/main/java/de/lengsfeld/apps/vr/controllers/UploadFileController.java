package de.lengsfeld.apps.vr.controllers;

import de.lengsfeld.apps.vr.repository.CemeteryRepository;
import de.lengsfeld.apps.vr.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UploadFileController {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CemeteryRepository cemeteryRepository;

}
