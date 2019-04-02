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
@DiscriminatorValue("grave")
public class GraveImage extends Image implements Serializable {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_GRAVE_ID"))
    private Grave grave;

    public GraveImage(String fileName, String format, byte[] imageData) {
        super(fileName, format, imageData);
    }

}