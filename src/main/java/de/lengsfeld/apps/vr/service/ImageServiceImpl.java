package de.lengsfeld.apps.vr.service;

import de.lengsfeld.apps.vr.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("imageService")
@Transactional
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    /*
        @Override
        public GraveImage findFirstImageByGrave(Grave grave) {
          GraveImage image = imageRepository.findFirstImageByGrave(grave);
          return image;
        }

        @Override
        public List<GraveImage> findByGrave(Grave grave) {
          List<GraveImage> images = imageRepository.findAllBy(grave);
          return images;
        }

    public GraveImage storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            GraveImage dbFile = new GraveImage(fileName, file.getContentType(), file.getBytes());

            return imageRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public GraveImage getFile(String fileId) {
        return imageRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }

    public void uploadImage(GraveImage image) {
        System.out.println("ImageServiceBean.java - uploadImage: image" + image);
        image.setImageData(getImage(image));
        imageRepository.save(image);
    }

    private byte[] getImage(GraveImage image) {
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
*/
}
