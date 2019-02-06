package de.lengsfeld.apps.vr.controllers;

import de.lengsfeld.apps.vr.entity.Cemetery;
import de.lengsfeld.apps.vr.entity.Grave;
import de.lengsfeld.apps.vr.repository.CemeteryRepository;
import de.lengsfeld.apps.vr.repository.GraveRepository;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {

    @Autowired
    private CemeteryRepository cemeteryRepository;

    @Autowired
    private GraveRepository graveRepository;

    @RequestMapping("**/partials/{page}")
    String partialHandler(@PathVariable("page") final String page) {
        return page;
    }

    @RequestMapping(value = {"/"})
    String home(ModelMap modal) {
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
        List<Grave> graves = graveRepository.findGraveByCemetery(cemetery);
        //model.addAttribute("graves", graves);
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
        return "cemeteries";
    }

    @GetMapping(value = "show-graves/{id}")
    public String showGraves(@PathVariable("id") long id, Model model){
        model.addAttribute("cemeteries", cemeteryRepository.findAll());
        Cemetery cemetery = cemeteryRepository.findById(id).get();
        List<Grave> graves = graveRepository.findGraveByCemetery(cemetery);
        model.addAttribute("graves", graves);
        return "cemeteries";
    }

}
