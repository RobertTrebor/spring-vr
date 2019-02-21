package de.lengsfeld.apps.vr.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "grave")
public class Grave implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_CEMETERY_ID"))
    private Cemetery cemetery;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Cemetery getCemetery() {
        return cemetery;
    }

    public void setCemetery(Cemetery cemetery) {
        this.cemetery = cemetery;
    }

    public LocalDateTime getCreated(){
        return created;
    }

    public void setCreated(LocalDateTime created){
        this.created = created;
    }

    public LocalDateTime getModified(){
        return modified;
    }

    public void setModified(LocalDateTime modified){
        this.modified = modified;
    }

}
