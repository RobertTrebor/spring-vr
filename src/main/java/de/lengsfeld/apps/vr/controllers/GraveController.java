package de.lengsfeld.apps.vr.controllers;

import de.lengsfeld.apps.vr.entity.Cemetery;
import de.lengsfeld.apps.vr.entity.FileInfo;
import de.lengsfeld.apps.vr.entity.Grave;
import de.lengsfeld.apps.vr.repository.CemeteryRepository;
import de.lengsfeld.apps.vr.repository.GraveRepository;
import de.lengsfeld.apps.vr.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
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

    @PostMapping(value = "/updategrave/{id}")
    public String showUpdateGrave(@PathVariable("id") long id, @Valid Grave grave, BindingResult result, Model model){
        if(result.hasErrors()){
            grave.setId(id);
            return "update-grave";
        }
        graveRepository.save(grave);
        model.addAttribute("cemeteries", cemeteryRepository.findAll());
        model.addAttribute("grave", grave);
        model.addAttribute("selectedcemetery", grave.getCemetery());
        model.addAttribute("selectedcemeteryid", grave.getCemetery().getId());
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
}
