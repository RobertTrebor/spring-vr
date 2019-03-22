package de.lengsfeld.apps.vr.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileInfo {

    private String filename;
    private String url;

    public FileInfo(String filename, String url) {
        this.filename = filename;
        this.url = url;
    }
}
