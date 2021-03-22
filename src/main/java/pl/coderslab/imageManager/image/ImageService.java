package pl.coderslab.imageManager.image;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.transaction.Transactional;
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
    private final int RELATIVE_IMAGE_PATH = 3;
    private final int IMAGE_FILE_PATH = 5;
    private final String MINIFIED_FILE_DIRECTORY = "_min.";
    private final String ORIGINAL_FILE_DIRECTORY = ".";
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Transactional
    public void saveImage(ImageInputStream inputStream, String name) throws IOException {
        List<String> formats = getFormatName(inputStream);
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        Image image = saveImageToDb(bufferedImage, name, formats.get(0));
        saveImageToFile(bufferedImage, image, false);
        BufferedImage resizedImage = resizedImage = Scalr.resize(bufferedImage, TARGET_HEIGHT);
        saveImageToFile(resizedImage, image, true);
    }

    public void deleteImage(long id) throws IOException {
        deleteInFile(id);
        imageRepository.deleteById(id);
    }

    private List<String> getFormatName(ImageInputStream inputStream) throws IOException {
        Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(inputStream);
        List<String> formatNames = new ArrayList<>();
        while (imageReaders.hasNext()) {
            ImageReader reader = imageReaders.next();
            formatNames.add(reader.getFormatName());
        }
        return formatNames;
    }

    private Image saveImageToDb(BufferedImage image, String name, String format)
            throws IOException {
        Image imageDb = new Image.ImageBuilder()
                .name(name)
                .width(image.getWidth())
                .height(image.getHeight())
                .format(format)
                .build();

        return imageRepository.save(imageDb);
    }

    private void savePath(Image image, String path, boolean isMiniature) {
        if (isMiniature) {
            image.setMinPath(path);
        } else {
            image.setPath(path);
        }
        imageRepository.save(image);
    }

    private boolean saveImageToFile(BufferedImage image, Image imageDescription, boolean isMiniature) {
        String imageType = imageDescription.getFormat().toLowerCase();
        String filePath = "src/main/webapp/images/" + imageDescription.getId() + "/"
                + imageDescription.getName();
        if (isMiniature) {
            filePath += MINIFIED_FILE_DIRECTORY + imageType;
        } else {
            filePath += ORIGINAL_FILE_DIRECTORY + imageType;
        }
        Path path = Paths.get(filePath);
        try {
            if (!Files.exists(path.subpath(0, IMAGE_FILE_PATH))) {
                Files.createDirectory(path.subpath(0, IMAGE_FILE_PATH));
            }
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            ImageIO.write(image, imageType, path.toFile());
        } catch (IOException exception) {
            return false;
        }
        savePath(imageDescription, path.subpath(RELATIVE_IMAGE_PATH, path.getNameCount()).toString(), isMiniature);
        return true;
    }

        private void deleteInFile(long id) throws IOException {
        Image image = imageRepository.getOne(id);
        String path = "src/main/webapp/" + image.getPath();
        String minPath = "src/main/webapp/" + image.getMinPath();
        if (Files.exists(Paths.get(path))) {
            Files.delete(Paths.get(path));
            Files.delete(Paths.get(minPath));
            Files.delete(Paths.get(path).subpath(0, IMAGE_FILE_PATH));
        }
    }
}
