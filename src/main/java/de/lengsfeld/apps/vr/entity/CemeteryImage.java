package de.lengsfeld.apps.vr.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("cemetery")
public class CemeteryImage extends Image implements Serializable {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_CEMETERY_IMAGE_ID"))
    private Cemetery cemetery;

    public CemeteryImage(String fileName, String format, byte[] imageData) {
        super(fileName, format, imageData);
    }

}