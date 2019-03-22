package de.lengsfeld.apps.vr.controllers;

import de.lengsfeld.apps.vr.entity.FileInfo;
import de.lengsfeld.apps.vr.entity.Image;
import de.lengsfeld.apps.vr.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DownloadFileController {

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping("/files")
    public String getListFiles(Model model) {
        List<FileInfo> fileInfos = imageRepository.findAll().stream().map(
                fileModel ->  {
                    String filename = fileModel.getFileName();
                    String url = MvcUriComponentsBuilder.fromMethodName(DownloadFileController.class,
                            "downloadFile", fileModel.getFileName().toString()).build().toString();
                    return new FileInfo(filename, url);
                }
        ).collect(Collectors.toList());

        model.addAttribute("files", fileInfos);
        return "listfiles";
    }

    /*
     * Download Files
     */
    @GetMapping("/files/{filename}/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename) {
        Image image = imageRepository.findImageByFileName(filename);
        if(image != null){
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                    .body(image.getImageData());
        } else {
            return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
        }
    }

}
