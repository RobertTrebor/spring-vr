package de.lengsfeld.apps.vr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "grave")
public class Grave implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "FIRSTNAME")
    @NotBlank
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_CEMETERY_ID"))
    private Cemetery cemetery;

    @OneToMany(mappedBy = "grave", fetch = FetchType.EAGER)
    private List<GraveImage> images;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date datebirth;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.YYYY")
    private Date datedeath;

    private String graveLoc;
    private String latitude;
    private String longitude;
    private String sex;
    private String tombstonePath;
    private String vitaPath;

    private LocalDateTime created;
    private LocalDateTime modified;

    public Grave() {
        created = LocalDateTime.now();
    }

    public Grave(String firstName, String lastName) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Grave(String firstName, String lastName, Cemetery cemetery) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.cemetery = cemetery;
    }

    @JsonIgnore
    public List<GraveImage> getImages() {
        if(images == null){
            return Collections.emptyList();
        }
        return images;
    }
}
