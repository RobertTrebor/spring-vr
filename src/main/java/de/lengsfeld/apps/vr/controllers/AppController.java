package de.lengsfeld.apps.vr.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping(value = {""})
public class AppController {

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

}
