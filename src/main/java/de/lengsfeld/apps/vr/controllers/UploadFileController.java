package de.lengsfeld.apps.vr.controllers;

import de.lengsfeld.apps.vr.entity.Cemetery;
import de.lengsfeld.apps.vr.entity.CemeteryImage;
import de.lengsfeld.apps.vr.entity.Image;
import de.lengsfeld.apps.vr.repository.CemeteryRepository;
import de.lengsfeld.apps.vr.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UploadFileController {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CemeteryRepository cemeteryRepository;

    @GetMapping(value = "/uploadform")
    public String showUploadForm(Model model){
        model.addAttribute("cemetery", cemeteryRepository.findById(1000L));
        return "uploadform";
    }

    @PostMapping(value = "/updatecemeteryimage/{id}")
    public String updateCemeteryImg(@PathVariable("id") long id,
                                    @RequestParam("files") MultipartFile[] files, Model model) {
        Cemetery cemetery = cemeteryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid: " + id));
        List<String> fileNames = new ArrayList<>();
        List<CemeteryImage> storedFiles = new ArrayList<>();
        try {
            for(MultipartFile file: files) {
                CemeteryImage image = (CemeteryImage) getImage(file);
                image.setCemetery(cemetery);
                storedFiles.add(image);
                fileNames.add(file.getOriginalFilename());
            }
            imageRepository.saveAll(storedFiles);
            model.addAttribute("message", "Files uploaded successfully!");
            model.addAttribute("files", fileNames);
        } catch (IOException e) {
            model.addAttribute("message", "Fail!");
            model.addAttribute("files", fileNames);
        }
        model.addAttribute("cemetery", cemetery);
        return "update-cemetery";
    }

    private Image getImage(MultipartFile file) throws IOException {
        Image image;
        Optional<Image> optionalImage = imageRepository.findById(file.getOriginalFilename());
        if (optionalImage.isPresent()) {
            image = optionalImage.get();
            image.setImageData(file.getBytes());
        } else {
            image = new CemeteryImage(file.getOriginalFilename(), file.getContentType(), file.getBytes());
        }
        return image;
    }

}
