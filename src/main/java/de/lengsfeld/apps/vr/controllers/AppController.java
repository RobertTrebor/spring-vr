package de.lengsfeld.apps.vr.controllers;

import de.lengsfeld.apps.vr.entity.Cemetery;
import de.lengsfeld.apps.vr.entity.Grave;
import de.lengsfeld.apps.vr.entity.Image;
import de.lengsfeld.apps.vr.repository.CemeteryRepository;
import de.lengsfeld.apps.vr.repository.GraveRepository;
import de.lengsfeld.apps.vr.repository.ImageRepository;
import de.lengsfeld.apps.vr.service.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class AppController {

    @Autowired
    private CemeteryRepository cemeteryRepository;

    @Autowired
    private GraveRepository graveRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageServiceImpl imageService;

    @RequestMapping("**/partials/{page}")
    String partialHandler(@PathVariable("page") final String page) {
        return page;
    }

    @RequestMapping(value = {"/"})
    String home(ModelMap modal) {
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

    @GetMapping(value = "cemeteries")
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

    @GetMapping(value = "show-graves/{id}")
    public String showGraves(@PathVariable("id") long id, Model model){
        model.addAttribute("cemeteries", cemeteryRepository.findAll());
        Cemetery cemetery = cemeteryRepository.findById(id).get();
        model.addAttribute("selectedcemetery", cemetery);
        List<Grave> graves = graveRepository.findGraveByCemetery(cemetery);
        model.addAttribute("graves", graves);
        return "cemeteries";
    }

    @GetMapping(value = "/add-grave/{id}")
    public String showAddGrave(Grave grave, @PathVariable("id") long id, Model model){
        Cemetery cemetery = cemeteryRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid: " + id));
        model.addAttribute("selectedcemeteryid", cemetery.getId());
        return "add-grave";
    }

    @PostMapping(value = "/addgrave")
    public String addGrave(@Valid Grave grave, BindingResult result, Model model){
        if(!cemeteryRepository.findById(grave.getCemetery().getId()).isPresent()){
            result.addError(new ObjectError("Cemetery", "Does Not Exist"));
        }
        if(result.hasErrors()){
            return "/add-grave";
        }
        Cemetery cemetery = cemeteryRepository.findById(grave.getCemetery().getId()).get();
        grave.setCemetery(cemetery);
        graveRepository.save(grave);
        model.addAttribute("cemeteries", cemeteryRepository.findAll());
        model.addAttribute("selectedcemetery", cemetery);
        List<Grave> graves = graveRepository.findGraveByCemetery(cemetery);
        model.addAttribute("graves", graves);
        return "cemeteries";
    }

    @GetMapping(value = "/editgrave/{id}")
    public String showUpdateGraveForm(@PathVariable("id") long id, Model model){
        Grave grave = graveRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid: " + id));
        model.addAttribute("grave", grave);
        model.addAttribute("selectedcemeteryid", grave.getCemetery().getId());
        return "update-grave";
    }

    @GetMapping(value = "/editgraveimage/{id}")
    public String showUpdateGraveImageForm(@PathVariable("id") long id, Model model){
        Grave grave = graveRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid: " + id));
        model.addAttribute("grave", grave);
        model.addAttribute("selectedcemeteryid", grave.getCemetery().getId());
        return "update-grave-image";
    }

    @PostMapping(value = "/updategraveimg/{id}/{imageupload}")
    public String uploadImage(@RequestParam("id") long id,
                              @RequestParam("imageupload") MultipartFile file, Model model) {
        Model m = model;
        Image dbFile = imageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();
        return "update-grave-image";
        //return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping(value = "/updategrave/{id}")
    public String showUpdateGrave(@PathVariable("id") long id, @Valid Grave grave, BindingResult result, Model model){
        if(result.hasErrors()){
            grave.setId(id);
            return "update-grave";
        }
        graveRepository.save(grave);
        model.addAttribute("cemeteries", cemeteryRepository.findAll());
        model.addAttribute("selectedcemetery", grave.getCemetery());
        return "cemeteries";
    }

    @GetMapping(value = "/updatecemeteryimg")
    public String showUpdateCemeteryImg(Model model){
        model.addAttribute("cemetery", cemeteryRepository.findById(1000L));
        return "uploadform";
    }

}
