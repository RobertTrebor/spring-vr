package de.lengsfeld.apps.vr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cemetery")
public class Cemetery implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "cemetery")
    private List<Grave> graves;

    public Cemetery() {
    }

    public Cemetery(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public List<Grave> getGraves() {
        return graves;
    }

    public void setGraves(List<Grave> graves) {
        this.graves = graves;
    }
}
