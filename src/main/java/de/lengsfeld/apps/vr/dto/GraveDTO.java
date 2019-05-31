package de.lengsfeld.apps.vr.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode
@Getter
@Setter
public class GraveDTO {

    private Long id;

    @NotBlank
    private String firstName;
    private String lastName;


    public GraveDTO() {
    }

    public GraveDTO(Long id, String firstName, String lastName) {
        this();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
