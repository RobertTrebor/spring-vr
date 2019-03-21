package de.lengsfeld.apps.vr.service;

import de.lengsfeld.apps.vr.entity.Image;
import de.lengsfeld.apps.vr.exceptions.FileNotFoundException;
import de.lengsfeld.apps.vr.exceptions.FileStorageException;
import de.lengsfeld.apps.vr.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Service("imageService")
@Transactional
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    /*
        @Override
        public Image findFirstImageByGrave(Grave grave) {
          Image image = imageRepository.findFirstImageByGrave(grave);
          return image;
        }

        @Override
        public List<Image> findByGrave(Grave grave) {
          List<Image> images = imageRepository.findAllBy(grave);
          return images;
        }
    */
    public Image storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Image dbFile = new Image(fileName, file.getContentType(), file.getBytes());

            return imageRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Image getFile(String fileId) {
        return imageRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }

    public void uploadImage(Image image) {
        System.out.println("ImageServiceBean.java - uploadImage: image" + image);
        image.setImageData(getImage(image));
        imageRepository.save(image);
    }

    private byte[] getImage(Image image) {
        File file = new File(image.getFileName());
        if (file.exists()) {
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "jpg", outputStream);
                return outputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
