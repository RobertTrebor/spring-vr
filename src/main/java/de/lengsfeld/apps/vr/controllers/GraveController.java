package de.lengsfeld.apps.vr.controllers;

import de.lengsfeld.apps.vr.entity.Cemetery;
import de.lengsfeld.apps.vr.entity.FileInfo;
import de.lengsfeld.apps.vr.entity.Grave;
import de.lengsfeld.apps.vr.entity.GraveImage;
import de.lengsfeld.apps.vr.repository.CemeteryRepository;
import de.lengsfeld.apps.vr.repository.GraveRepository;
import de.lengsfeld.apps.vr.repository.ImageRepository;
import de.lengsfeld.apps.vr.service.GraveServiceDTO;
import de.lengsfeld.apps.vr.util.ImageUtils;
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
public class GraveController {

    @Autowired
    private CemeteryRepository cemeteryRepository;

    @Autowired
    private GraveRepository graveRepository;

    @Autowired
    private GraveServiceDTO graveServiceDTO;

    @Autowired
    private ImageRepository imageRepository;

    @RequestMapping(value = "allgraves", method = RequestMethod.GET)
    public Iterable<Grave> getAllGraves() {
        return graveRepository.findAll();
    }

    @GetMapping(value = "show-graves/{id}")
    public String showGraves(@PathVariable("id") long id, Model model){
        model.addAttribute("cemeteries", cemeteryRepository.findAll());
        Cemetery cemetery = cemeteryRepository.findById(id).get();
        model.addAttribute("selectedcemetery", cemetery);
        List<Grave> graves = graveRepository.findGraveByCemetery(cemetery);
        model.addAttribute("graves", graves);
        return "cemeteries";
    }

    @PostMapping(value = "/add-grave")
    public String showAddGrave(Grave grave, Model model){
        Cemetery cemetery = grave.getCemetery();
        model.addAttribute("selectedcemeteryid", cemetery.getId());
        model.addAttribute("grave", grave);
        return "update-grave";
    }

    @GetMapping(value = "/editgrave/{id}")
    public String showUpdateGraveForm(@PathVariable("id") long id, Model model){
        Grave grave = graveRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid: " + id));
        model.addAttribute("grave", grave);
        model.addAttribute("selectedcemeteryid", grave.getCemetery().getId());
        return "update-grave";
    }

    @PostMapping(value = "/updategraveimage/{id}")
    public String uploadImage(@PathVariable("id") long id,
                              @RequestParam("files") MultipartFile[] files, Model model) {
        Grave grave = graveRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid: " + id));
        List<String> fileNames = new ArrayList<>();
        List<GraveImage> storedFile = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                GraveImage image = (GraveImage) ImageUtils.getImage(file, new GraveImage());
                image.setGrave(grave);
                storedFile.add(image);
                fileNames.add(file.getOriginalFilename());
            }
            imageRepository.saveAll(storedFile);
            model.addAttribute("message", "Files uploaded successfully!");
            model.addAttribute("files", fileNames);
        } catch (Exception e) {
            model.addAttribute("message", "Fail!");
            model.addAttribute("files", fileNames);
        }
        model.addAttribute("grave", grave);
        model.addAttribute("selectedcemeteryid", grave.getCemetery().getId());
        return "update-grave";
    }

    @PostMapping(value = "/updategrave")
    public String showUpdateGrave(@Valid Grave grave, BindingResult result, Model model){
        if (!cemeteryRepository.findById(grave.getCemetery().getId()).isPresent()) {
            result.addError(new ObjectError("Cemetery", "Does Not Exist"));
        }
        if(result.hasErrors()){ ;
            model.addAttribute("grave", grave);
            model.addAttribute("selectedcemeteryid", grave.getCemetery().getId());
            return "update-grave";
        }
        grave = graveRepository.save(grave);
        model.addAttribute("cemeteries", cemeteryRepository.findAll());
        model.addAttribute("grave", grave);
        model.addAttribute("selectedcemetery", grave.getCemetery());
        List<Grave> graves = graveRepository.findGraveByCemetery(grave.getCemetery());
        model.addAttribute("graves", graves);
        return "cemeteries";
    }

    @PostMapping(value = "/updategrave/{id}")
    public String showUpdateGrave(@PathVariable("id") long id, @Valid Grave grave, BindingResult result, Model model){
        if (!cemeteryRepository.findById(grave.getCemetery().getId()).isPresent()) {
            result.addError(new ObjectError("Cemetery", "Does Not Exist"));
        }
        if(result.hasErrors()){
            grave.setId(id);
            return "update-grave";
        }
        grave = graveRepository.save(grave);
        model.addAttribute("cemeteries", cemeteryRepository.findAll());
        model.addAttribute("grave", grave);
        model.addAttribute("selectedcemetery", grave.getCemetery());
        List<Grave> graves = graveRepository.findGraveByCemetery(grave.getCemetery());
        model.addAttribute("graves", graves);
        return "cemeteries";
    }

    @GetMapping("/gravefiles/{id}")
    public String getListFiles(@PathVariable("id") long id, Model model) {
        Optional<Grave> optionalGrave = graveRepository.findById(id);
        if(optionalGrave.isPresent()){
            Grave grave = optionalGrave.get();
            List<FileInfo> fileInfos = imageRepository.findImagesByGrave(grave).stream().map(
                    fileModel -> {
                        String filename = fileModel.getFileName();
                        String url = MvcUriComponentsBuilder.fromMethodName(DownloadFileController.class,
                                "downloadFile", fileModel.getFileName().toString()).build().toString();
                        return new FileInfo(filename, url);
                    }
            ).collect(Collectors.toList());
            model.addAttribute("name", grave.getFirstName() + " " + grave.getLastName());
            model.addAttribute("grave", grave);
            model.addAttribute("files", fileInfos);
        }
        return "listfiles";
    }

    @GetMapping(value = "/graveimageDisplay/{id}")
    @ResponseBody
    public void showImage(@PathVariable("id") long id, HttpServletResponse response, HttpServletRequest request)
            throws ServletException, IOException {
        Grave grave = graveRepository.findById(id).get();
        List<GraveImage> images = imageRepository.findImagesByGrave(grave);
        if (images != null && !images.isEmpty()) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(images.get(0).getImageData());
        }
        response.getOutputStream().close();
    }

    @DeleteMapping(value = "/delete")
    public String delete(@RequestParam Long id){
        graveServiceDTO.delete(id);
        return "redirect:/";
    }


}
