package de.lengsfeld.apps.vr.controllers;

import de.lengsfeld.apps.vr.entity.Image;
import de.lengsfeld.apps.vr.repository.CemeteryRepository;
import de.lengsfeld.apps.vr.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(value = "/updatecemeteryimg")
    public String updateCemeteryImg(@RequestParam("files") MultipartFile[] files, Model model){
        List<String> fileNames = new ArrayList<>();
        try {
            List<Image> storedFile = new ArrayList<>();

            for(MultipartFile file: files) {
                Optional<Image> optionalImage = imageRepository.findById(file.getOriginalFilename());
                Image image = null;
                if(optionalImage.isPresent()){
                    image = optionalImage.get();
                }
                if(image != null) {
                    image.setImageData(file.getBytes());
                } else {
                    image = new Image(file.getOriginalFilename(), file.getContentType(), file.getBytes());
                }
                fileNames.add(file.getOriginalFilename());
                storedFile.add(image);
            }
            imageRepository.saveAll(storedFile);
            model.addAttribute("message", "Files uploaded successfully!");
            model.addAttribute("files", fileNames);
        } catch (Exception e) {
            model.addAttribute("message", "Fail!");
            model.addAttribute("files", fileNames);
        }
        return "uploadform";
    }

}
