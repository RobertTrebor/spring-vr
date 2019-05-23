package de.lengsfeld.apps.vr.controllers;

import de.lengsfeld.apps.vr.entity.Image;
import de.lengsfeld.apps.vr.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DownloadFileController {

    @Autowired
    private ImageRepository imageRepository;

    /*
     * Download Files
     */
    @GetMapping("/files/{filename}/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename) {
        Image image = imageRepository.findImageByFileName(filename);
        if(image != null){
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                    .body(image.getImageData());
        } else {
            return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
        }
    }

}
