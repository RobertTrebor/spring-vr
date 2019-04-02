package de.lengsfeld.apps.vr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "cemetery")
public class Cemetery implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
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

    public Cemetery(String name) {
        this.name = name;
    }

    @JsonIgnore
    public List<Grave> getGraves() {
        return graves;
    }

    public Image getImage() {
        if (images != null && !images.isEmpty()) {
            return images.get(0);
        }
        return null;
    }
}
