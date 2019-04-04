package de.lengsfeld.apps.vr.util;

import de.lengsfeld.apps.vr.entity.Image;
import org.imgscalr.Scalr;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtils {

    public static Image getImage(MultipartFile file, Image image) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        bufferedImage = getResizedImage(bufferedImage);
        long fileSize = file.getSize();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( bufferedImage, "jpg", baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();

        ImageWriter imageWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(baos);
        imageWriter.setOutput(imageOutputStream);
        ImageWriteParam param = imageWriter.getDefaultWriteParam();
        if(param.canWriteCompressed()){
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            if(fileSize > 4000000) {
                param.setCompressionQuality(0.0005f);
            } else if(fileSize > 1000000) {
                param.setCompressionQuality(0.005f);
            } else if(fileSize > 100000){
                param.setCompressionQuality(0.05f);
            }
        }
        imageWriter.write(null, new IIOImage(bufferedImage, null, null), param);
        baos.close();
        imageOutputStream.close();
        imageWriter.dispose();
        image.setFileName(file.getOriginalFilename());
        image.setFormat(file.getContentType());
        image.setImageData(imageInByte);
        return image;
    }

    private static BufferedImage getResizedImage(BufferedImage bufferedImage){
        bufferedImage = Scalr.resize(bufferedImage, 400, 400);
        return bufferedImage;
    }
}
