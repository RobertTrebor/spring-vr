package de.lengsfeld.apps.vr.controllers;

import de.lengsfeld.apps.vr.entity.Cemetery;
import de.lengsfeld.apps.vr.entity.CemeteryImage;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
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

    @GetMapping(value = "/cemeteries/{id}")
    public String showCemeteries(@PathVariable("id") long id, Model model){
        List<Cemetery> cemeteries = cemeteryRepository.findAll();
        model.addAttribute("cemeteries", cemeteries);
        Cemetery cemetery = cemeteryRepository.findById(id).get();
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
        return "cemeteries";
    }

    @GetMapping(value = "{id}/edit")
    public String showUpdateForm(@PathVariable("id") long id, Model model){
        Cemetery cemetery = cemeteryRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid: " + id));
        model.addAttribute("cemetery", cemetery);
        return "update-cemetery";
    }

    @PostMapping(value = "{id}/update")
    public String showUpdateCemetery(@PathVariable("id") long id, @Valid Cemetery cemetery, BindingResult result, Model model){
        if(result.hasErrors()){
            cemetery.setId(id);
            return "update-cemetery";
        }
        cemeteryRepository.save(cemetery);
        model.addAttribute("cemeteries", cemeteryRepository.findAll());
        model.addAttribute("cemetery", cemetery);
        return "cemeteries";
    }

    @GetMapping(value = "/cemeteries/{id}/imageDisplay")
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

}
