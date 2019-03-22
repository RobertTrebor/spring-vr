package de.lengsfeld.apps.vr.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "IMAGE")
public class Image implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "URL")
    private String URL;

    @Column(name = "FORMAT", length = 8)
    private String format;

    @Lob
    @Column(name = "IMAGE_DATA", nullable = true, columnDefinition = "longblob")
    private byte[] imageData;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_GRAVE_ID"))
    private Grave grave;


    public Image(String fileName, String format, byte[] imageData) {
        this.fileName = fileName;
        this.format = format;
        this.imageData = imageData;
    }

    public Image(String fileName, String URI){

    }

    public BufferedImage getBufferedImage() {
        BufferedImage bufferedImage = null;
        try (InputStream inputStream = new ByteArrayInputStream(imageData)) {
            bufferedImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "JPG", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageData = out.toByteArray();
    }

}