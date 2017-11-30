package de.lengsfeld.apps.vr.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class Grave implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;

    @ManyToOne
    private Cemetery cemetery;

    protected Grave() {
    }

    public Grave(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Grave(String firstName, String lastName, Cemetery cemetery) {
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
}
