package de.lengsfeld.apps.vr.controllers;

import de.lengsfeld.apps.vr.entity.Grave;
import de.lengsfeld.apps.vr.model.AjaxResponseBody;
import de.lengsfeld.apps.vr.model.GraveForm;
import de.lengsfeld.apps.vr.repository.GraveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AppController {

    @Autowired
    private GraveRepository graveRepository;


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

    @PostMapping("/update-grave")
    public ResponseEntity<?> getSearchResultViaAjax(@Valid @RequestBody GraveForm graveForm, Errors errors) {

        AjaxResponseBody result = new AjaxResponseBody();

        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {
            result.setMsg(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(result);
        }
        String id = graveForm.getId();
        Long idlong = Long.valueOf(id);
        Grave grave = graveRepository.findById(idlong).get();
        grave.setFirstName(graveForm.getFirstName());
        grave.setLastName(graveForm.getLastName());
        graveRepository.save(grave);
        return ResponseEntity.ok(result);

    }

}
