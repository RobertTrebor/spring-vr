package de.lengsfeld.apps.vr.controllers;

import de.lengsfeld.apps.vr.entity.*;
import de.lengsfeld.apps.vr.repository.CemeteryRepository;
import de.lengsfeld.apps.vr.repository.GraveRepository;
import de.lengsfeld.apps.vr.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
@RequestMapping(value = "cemeteries/{id}")
public class GraveController {

    @Autowired
    private CemeteryRepository cemeteryRepository;

    @Autowired
    private GraveRepository graveRepository;

    @Autowired
    private ImageRepository imageRepository;

    @RequestMapping(value = "allgraves", method = RequestMethod.GET)
    public Iterable<Grave> getAllGraves() {
        return graveRepository.findAll();
    }

    @GetMapping(value = "/graves")
    public String showGraves(@PathVariable("id") long id, Model model){
        model.addAttribute("cemeteries", cemeteryRepository.findAll());
        Cemetery cemetery = cemeteryRepository.findById(id).get();
        List<Grave> graves = graveRepository.findGraveByCemetery(cemetery);
        model.addAttribute("graves", graves);
        return "cemeteries";
    }

    @GetMapping(value = "/graves/{graveid}/add-grave")
    public String showAddGrave(Grave grave, Model model){
        model.addAttribute("grave", grave);
        return "update-grave";
    }

    @GetMapping(value = "/graves/{graveid}/editgrave")
    public String showUpdateGraveForm(@PathVariable("graveid") long graveid, Model model){
        Grave grave = graveRepository.findById(graveid).orElseThrow(()-> new IllegalArgumentException("Invalid: " + graveid));
        model.addAttribute("grave", grave);
        return "update-grave";
    }

    @PostMapping(value = "/graves/{graveid}/updategraveimage")
    public String uploadImage(@PathVariable("graveid") long id,
                              @RequestParam("files") MultipartFile[] files, Model model) {
        Grave grave = graveRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid: " + id));
        List<String> fileNames = new ArrayList<>();
        try {
            List<GraveImage> storedFile = new ArrayList<>();

            for (MultipartFile file : files) {
                Optional<Image> optionalImage = imageRepository.findById(file.getOriginalFilename());
                GraveImage image;
                if (optionalImage.isPresent()) {
                    image = (GraveImage) optionalImage.get();
                    image.setImageData(file.getBytes());
                } else {
                    image = new GraveImage(file.getOriginalFilename(), file.getContentType(), file.getBytes());
                }
                fileNames.add(file.getOriginalFilename());
                image.setGrave(grave);
                storedFile.add(image);
            }
            imageRepository.saveAll(storedFile);
            model.addAttribute("message", "Files uploaded successfully!");
            model.addAttribute("files", fileNames);
            model.addAttribute("grave", grave);
        } catch (Exception e) {
            model.addAttribute("message", "Fail!");
            model.addAttribute("files", fileNames);
        }
        return "update-grave";
    }

    @PostMapping(value = "/graves/{graveid}/updategrave")
    public String showUpdateGrave(@PathVariable("id") long id, @Valid Grave grave, BindingResult result, Model model){
        Optional<Cemetery> cemetery = cemeteryRepository.findById(id);
        if (!cemetery.isPresent()) {
            result.addError(new ObjectError("Cemetery", "Does Not Exist"));
        } else {
            grave.setCemetery(cemetery.get());
        }
        if(result.hasErrors()){
            model.addAttribute("grave", grave);
            return "update-grave";
        }
        graveRepository.save(grave);
        model.addAttribute("cemeteries", cemeteryRepository.findAll());
        return "cemeteries";
    }

    @GetMapping("/files/{id}")
    public String getListFiles(@PathVariable("id") long id, Model model) {
        Grave grave = graveRepository.getOne(id);
        List<FileInfo> fileInfos = imageRepository.findImagesByGrave(grave).stream().map(
                fileModel -> {
                    String filename = fileModel.getFileName();
                    String url = MvcUriComponentsBuilder.fromMethodName(DownloadFileController.class,
                            "downloadFile", fileModel.getFileName().toString()).build().toString();
                    return new FileInfo(filename, url);
                }
        ).collect(Collectors.toList());
        model.addAttribute("grave", grave);
        model.addAttribute("files", fileInfos);
        return "listfiles";
    }

    @GetMapping(value = "/graves/{graveid}/graveimageDisplay")
    @ResponseBody
    public void showImage(@PathVariable("graveid") long id, HttpServletResponse response, HttpServletRequest request)
            throws ServletException, IOException {
        Grave grave = graveRepository.findById(id).get();
        List<GraveImage> images = imageRepository.findImagesByGrave(grave);
        if (images != null && !images.isEmpty()) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(images.get(0).getImageData());
        }
        response.getOutputStream().close();
    }

}
