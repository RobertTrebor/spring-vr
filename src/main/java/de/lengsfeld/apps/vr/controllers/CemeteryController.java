package de.lengsfeld.apps.vr.controllers;

import de.lengsfeld.apps.vr.entity.Cemetery;
import de.lengsfeld.apps.vr.entity.CemeteryImage;
import de.lengsfeld.apps.vr.entity.FileInfo;
import de.lengsfeld.apps.vr.entity.Grave;
import de.lengsfeld.apps.vr.repository.CemeteryRepository;
import de.lengsfeld.apps.vr.repository.GraveRepository;
import de.lengsfeld.apps.vr.repository.ImageRepository;
import de.lengsfeld.apps.vr.util.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/cemeteries/{id}")
public class CemeteryController {

    public static final Logger logger = LoggerFactory.getLogger(CemeteryController.class);

    @Autowired
    private CemeteryRepository cemeteryRepository;

    @Autowired
    private GraveRepository graveRepository;

    @Autowired
    private ImageRepository imageRepository;

    @ModelAttribute("cemeteries")
    public List<Cemetery> cemeteries() {
        return cemeteryRepository.findAll();
    }

    @GetMapping(value = "/")
    public String showCemeteries(@PathVariable("id") long id, Model model){
        Optional<Cemetery> cemetery = cemeteryRepository.findById(id);
        if(cemetery.isPresent()) {
            List<Cemetery> cemeteries = cemeteryRepository.findAll();
            model.addAttribute("cemeteries", cemeteries);
            List<Grave> graves = graveRepository.findGraveByCemetery(cemetery.get());
            model.addAttribute("graves", graves);
            return "cemeteries";
        } else {
            return "index";
        }
    }

    @GetMapping(value = "add-cemetery")
    public String showAddCemetery(Cemetery cemetery, Model model){
        model.addAttribute("cemetery", cemetery);
        return "update-cemetery";
    }

    @GetMapping(value = "edit")
    public String showUpdateForm(@PathVariable("id") long id, Model model){
        Cemetery cemetery = cemeteryRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid: " + id));
        model.addAttribute("cemetery", cemetery);
        return "update-cemetery";
    }

    @GetMapping(value = "delete")
    public String deleteCemetery(@PathVariable("id") long id, Model model){
        Cemetery cemetery = cemeteryRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid: " + id));
        List<CemeteryImage> cemeteryImages = imageRepository.findImagesByCemetery(cemetery);
        imageRepository.deleteAll(cemeteryImages);
        cemeteryRepository.delete(cemetery);
        model.addAttribute("cemeteries", cemeteryRepository.findAll());
        return "cemeteries";
    }

    @PostMapping(value = "update")
    public String showUpdateCemetery(@PathVariable("id") long id, @Valid Cemetery cemetery, BindingResult result, Model model){
        if(result.hasErrors()){
            cemetery.setId(id);
            return "update-cemetery";
        }
        cemeteryRepository.save(cemetery);
        model.addAttribute("cemetery", cemetery);
        return "cemeteries";
    }

    @GetMapping(value = "imageDisplay")
    @ResponseBody
    public void showImage(@PathVariable("id") long id, HttpServletResponse response, HttpServletRequest request)
            throws ServletException, IOException {
        Optional<Cemetery> cemetery = cemeteryRepository.findById(id);
        if(cemetery.isPresent()) {
            List<CemeteryImage> images = imageRepository.findImagesByCemetery(cemetery.get());
            if (images != null && !images.isEmpty()) {
                response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
                response.getOutputStream().write(images.get(0).getImageData());
            }
        }
        response.getOutputStream().close();
    }

    @PostMapping(value = "updatecemeteryimage")
    public String updateCemeteryImg(@PathVariable("id") long id,
                                    @RequestParam("files") MultipartFile[] files, Model model) {
        Cemetery cemetery = cemeteryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid: " + id));
        List<String> fileNames = new ArrayList<>();
        List<CemeteryImage> storedFiles = new ArrayList<>();
        try {
            for(MultipartFile file: files) {
                CemeteryImage image = (CemeteryImage) ImageUtils.getImage(file, new CemeteryImage());
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

    @GetMapping("files")
    public String getListFiles(@PathVariable("id") long id, Model model) {
        Optional<Cemetery> optional = cemeteryRepository.findById(id);
        if(optional.isPresent()){
            Cemetery cemetery = optional.get();
            List<FileInfo> fileInfos = imageRepository.findImagesByCemetery(cemetery).stream().map(
                    fileModel -> {
                        String filename = fileModel.getFileName();
                        String url = MvcUriComponentsBuilder.fromMethodName(DownloadFileController.class,
                                "downloadFile", fileModel.getFileName().toString()).build().toString();
                        return new FileInfo(filename, url);
                    }
            ).collect(Collectors.toList());
            model.addAttribute("name", cemetery.getName() + ", " + cemetery.getCity());
            model.addAttribute("cemetery", cemetery);
            model.addAttribute("files", fileInfos);
        }
        return "listfiles";
    }

}
