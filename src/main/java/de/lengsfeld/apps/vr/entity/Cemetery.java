package de.lengsfeld.apps.vr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "cemetery")
public class Cemetery implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;
    private String city;
    private String country;
    private String latitude;
    private String longitude;
    private String street;
    private String zipcode;

    @OneToMany(mappedBy = "cemetery")
    private List<Grave> graves;

    @OneToMany(mappedBy = "cemetery")
    private List<CemeteryImage> images;

    private LocalDateTime created;
    private LocalDateTime modified;

    public Cemetery(){
        created = LocalDateTime.now();
    }

    public Cemetery(String name) {
        this();
        this.name = name;
    }

    @JsonIgnore
    public List<Grave> getGraves() {
        return graves;
    }

    @JsonIgnore
    public List<CemeteryImage> getImages(){
        return images;
    }

    public Image getImage() {
        if (images != null && !images.isEmpty()) {
            return images.get(0);
        }
        return null;
    }
}
