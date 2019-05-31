package de.lengsfeld.apps.vr.controllers;

import de.lengsfeld.apps.vr.entity.Cemetery;
import de.lengsfeld.apps.vr.entity.CemeteryImage;
import de.lengsfeld.apps.vr.entity.FileInfo;
import de.lengsfeld.apps.vr.entity.Grave;
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
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public String showAddCemetery(Cemetery cemetery, Model model){
        model.addAttribute("cemetery", cemetery);
        return "update-cemetery";
    }

    @GetMapping(value = "/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model){
        Cemetery cemetery = cemeteryRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid: " + id));
        model.addAttribute("cemetery", cemetery);
        return "update-cemetery";
    }

    @PostMapping(value = "/update")
    public String showUpdateCemetery(@Valid Cemetery cemetery, BindingResult result, Model model){
        if(result.hasErrors()){
            return "update-cemetery";
        }
        cemetery = cemeteryRepository.save(cemetery);
        model.addAttribute("cemeteries", cemeteryRepository.findAll());
        model.addAttribute("cemetery", cemetery);
        model.addAttribute("selectedcemetery", cemetery);
        List<Grave> graves = graveRepository.findGraveByCemetery(cemetery);
        model.addAttribute("graves", graves);
        return "cemeteries";
    }

    @PostMapping(value = "/update/{id}")
    public String showUpdateCemetery(@PathVariable("id") long id, @Valid Cemetery cemetery, BindingResult result, Model model){
        if(result.hasErrors()){
            cemetery.setId(id);
            return "update-cemetery";
        }
        cemetery = cemeteryRepository.save(cemetery);
        model.addAttribute("cemeteries", cemeteryRepository.findAll());
        model.addAttribute("cemetery", cemetery);
        model.addAttribute("selectedcemetery", cemetery);
        List<Grave> graves = graveRepository.findGraveByCemetery(cemetery);
        model.addAttribute("graves", graves);
        return "cemeteries";
    }

    @GetMapping("/files/{id}")
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

    @GetMapping(value = "/imageDisplay/{id}")
    @ResponseBody
    public void showImage(@PathVariable("id") long id, HttpServletResponse response, HttpServletRequest request)
            throws ServletException, IOException {

        Cemetery cemetery = cemeteryRepository.findById(id).get();
        List<CemeteryImage> images = imageRepository.findImagesByCemetery(cemetery);
        if (images != null && !images.isEmpty()) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(images.get(0).getImageData());
        }
        response.getOutputStream().close();
    }

    @DeleteMapping(value = "/deletecemetery/{id}")
    public String delete(@PathVariable("id") long id){
        Optional<Cemetery> cemetery = cemeteryRepository.findById(id);
        if(cemetery.isPresent()) {
            if(graveRepository.findGraveByCemetery(cemetery.get()).isEmpty()) {
                if(imageRepository.findImagesByCemetery(cemetery.get()).isEmpty()) {
                    cemeteryRepository.delete(cemetery.get());
                }
            }
        }
        return "redirect:/listcemeteries";
    }

    @DeleteMapping(value = "/deletecemeteryimages/{id}")
    public String deleteImages(@PathVariable("id") long id){
        Optional<Cemetery> optional = cemeteryRepository.findById(id);
        if(optional.isPresent()){
            Cemetery cemetery = optional.get();
            List<CemeteryImage> images = imageRepository.findImagesByCemetery(cemetery);
            imageRepository.deleteAll(images);
        }
        return "redirect:/listcemeteries";
    }

}
