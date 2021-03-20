package pl.coderslab.imageManager.services;

import org.hibernate.boot.Metadata;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import pl.coderslab.imageManager.model.ImageDescription;
import pl.coderslab.imageManager.repositories.ImageRepository;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ImageService {
    private final int TARGET_HEIGHT = 150;
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    private List<String> getImageFormat(ImageInputStream inputStream) throws IOException {
        Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(inputStream);
        List<String> formatNames = new ArrayList<>();
        while (imageReaders.hasNext()) {
            ImageReader reader = (ImageReader) imageReaders.next();
            formatNames.add(reader.getFormatName());
        }
        return formatNames;
    }

    private ImageDescription saveImageToDb(BufferedImage image, String name, String format)
            throws IOException {
        ImageDescription imageDescription = new ImageDescription();
        imageDescription.setName(name);
        imageDescription.setWidth(image.getWidth());
        imageDescription.setHeight(image.getHeight());
        imageDescription.setFormat(format);

        imageRepository.save(imageDescription);
        return imageDescription;
    }

    private void savePath(ImageDescription imageDescription, String path, boolean isMiniature) {
        if (isMiniature) {
            imageDescription.setMinPath(path);
        } else {
            imageDescription.setPath(path);
        }
        imageRepository.save(imageDescription);
    }

    private boolean saveImageToFile(BufferedImage image, ImageDescription imageDescription, boolean isMiniature) {
        String imageType = imageDescription.getFormat().toLowerCase();
        String filePath = "src/main/webapp/images/" + imageDescription.getId() + "/"
                + imageDescription.getName();
        if (isMiniature) {
            filePath += "_min." + imageType;
        } else {
            filePath += "." + imageType;
        }
        Path path = Paths.get(filePath);
        try {
            if (!Files.exists(path.subpath(0,5))) {
                Files.createDirectory(path.subpath(0, 5));
            }
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            ImageIO.write(image, imageType, path.toFile());
        } catch (IOException exception) {
            return false;
        }
        savePath(imageDescription, path.subpath(3, path.getNameCount()).toString(), isMiniature);
        return true;
    }

    private BufferedImage resizeImage(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        double targetWidth = (double) TARGET_HEIGHT / image.getHeight() * image.getWidth();
        BufferedImage resizedImage = Scalr.resize(image, TARGET_HEIGHT);
        return resizedImage;
    }

    public ImageDescription saveImage(ImageInputStream inputStream, String name) throws IOException {
        List<String> formats = getImageFormat(inputStream);
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        ImageDescription imageDescription = saveImageToDb(bufferedImage, name, formats.get(0));
        boolean imageToFile = saveImageToFile(bufferedImage, imageDescription, false);
        boolean miniatureToFile = saveImageToFile(resizeImage(bufferedImage), imageDescription, true);
        if (imageToFile && miniatureToFile) {
            return imageDescription;
        }
        return null;
    }

    public List<ImageDescription> findAll() {
        return imageRepository.findAll();
    }

    public void deleteImageInDb(long id) {
        imageRepository.deleteById(id);
    }

    public void deleteImageInFile(long id) throws IOException {
        ImageDescription image = imageRepository.getOne(id);
        String path = "src/main/webapp/" + image.getPath();
        String minPath = "src/main/webapp/" + image.getMinPath();
        if (Files.exists(Paths.get(path))){
            Files.delete(Paths.get(path));
            Files.delete(Paths.get(minPath));
            Files.delete(Paths.get(path).subpath(0,5));
        }
    }
}
