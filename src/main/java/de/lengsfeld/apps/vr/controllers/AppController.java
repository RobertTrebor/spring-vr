package de.lengsfeld.apps.vr.controllers;

import de.lengsfeld.apps.vr.entity.Cemetery;
import de.lengsfeld.apps.vr.entity.Grave;
import de.lengsfeld.apps.vr.entity.Image;
import de.lengsfeld.apps.vr.repository.CemeteryRepository;
import de.lengsfeld.apps.vr.repository.GraveRepository;
import de.lengsfeld.apps.vr.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class AppController {

    @Autowired
    private CemeteryRepository cemeteryRepository;

    @Autowired
    private GraveRepository graveRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Value("${app.security.basic.enabled}")
    private String security;

    @RequestMapping("**/partials/{page}")
    String partialHandler(@PathVariable("page") final String page) {
        return page;
    }

    @RequestMapping(value = {"/"})
    String home(ModelMap modal) {
        modal.addAttribute("security", security);
        modal.addAttribute("title", "Virtual Remembrance");
        return "login";
    }

    @PostMapping(value = {"/signin"})
    public String getLogin(Model model){
        Map<String, Object> map = model.asMap();
        return "";
    }

    @RequestMapping(value = {"/index"})
    String index(ModelMap modal) {
        modal.addAttribute("title", "Virtual Remembrance");
        return "index";
    }

    @GetMapping("/t")
    public String home(){
        return "home";
    }

    @GetMapping(value = "listcemeteries")
    public String showCemeteries(Model model){
        List<Cemetery> cemeteries = cemeteryRepository.findAll();
        model.addAttribute("cemeteries", cemeteries);
        Cemetery cemetery = cemeteryRepository.findById(1L).get();
        model.addAttribute("selectedcemetery", cemetery);
        List<Grave> graves = graveRepository.findGraveByCemetery(cemetery);
        model.addAttribute("graves", graves);
        return "cemeteries";
    }

    @GetMapping(value = "/add-cemetery")
    public String showAddCemetery(Cemetery cemetery){
        return "add-cemetery";
    }

    @PostMapping("/addcemetery")
    public String addCemetery(@Valid Cemetery cemetery, BindingResult result, Model model){
        if(result.hasErrors()){
            return "/add-cemetery";
        }
        cemeteryRepository.save(cemetery);
        model.addAttribute("cemeteries", cemeteryRepository.findAll());
        model.addAttribute("selectedcemetery", cemetery);
        return "cemeteries";
    }

    @GetMapping(value = "/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model){
        Cemetery cemetery = cemeteryRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid: " + id));
        model.addAttribute("cemetery", cemetery);
        return "update-cemetery";
    }

    @PostMapping(value = "/update/{id}")
    public String showUpdateCemetery(@PathVariable("id") long id, @Valid Cemetery cemetery, BindingResult result, Model model){
        if(result.hasErrors()){
            cemetery.setId(id);
            return "update-cemetery";
        }
        cemeteryRepository.save(cemetery);
        model.addAttribute("cemeteries", cemeteryRepository.findAll());
        model.addAttribute("selectedcemetery", cemetery);
        return "cemeteries";
    }

    @GetMapping(value = "/editgraveimage/{id}")
    public String showUpdateGraveImageForm(@PathVariable("id") long id, Model model){
        Grave grave = graveRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid: " + id));
        model.addAttribute("grave", grave);
        model.addAttribute("selectedcemeteryid", grave.getCemetery().getId());
        return "update-grave-image";
    }

    @GetMapping(value = "/editgraveimage/{id}/updategraveimage")
    public String showUpdateGraveImageForm2(@PathVariable("id") long id, Model model){
        Grave grave = graveRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid: " + id));
        model.addAttribute("grave", grave);
        model.addAttribute("selectedcemeteryid", grave.getCemetery().getId());
        return "update-grave-image";
    }

    @PostMapping(value = "/updategraveimage/{id}")
    public String uploadImage(@PathVariable("id") long id,
                              @RequestParam("files") MultipartFile[] files, Model model) {
        Grave grave = graveRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid: " + id));
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
        return "update-grave-image";
    }

    @GetMapping(value = "/updatecemeteryimg")
    public String showUpdateCemeteryImg(Model model){
        model.addAttribute("cemetery", cemeteryRepository.findById(1000L));
        return "uploadform";
    }

}
