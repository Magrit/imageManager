package pl.coderslab.imageManager.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pl.coderslab.imageManager.model.ImageDescription;
import pl.coderslab.imageManager.repositories.ImageRepository;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ImageServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ImageServiceTest.class);
    private ImageService imageService;
    private ImageRepository imageRepository;

    Path path = Paths.get("src/main/webapp/images/0/fruits.jpeg");
    File file = path.toFile();

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setUp() {
        imageRepository = mock(ImageRepository.class);
        imageService = new ImageService(imageRepository);
    }

    @Test
    public void when_saving_image_return_image() {
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(bufferedImage);
            List<IIOMetadata> imageMetadata = new ArrayList<>();
            while (imageReaders.hasNext()) {
                imageMetadata.add(imageReaders.next().getStreamMetadata());
            }
            byte[] bytes = Files.readAllBytes(path);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
            ImageDescription fruits = imageService.saveImage(imageInputStream, "fruits");
            String fruitsPath = "src/main/webapp/" + fruits.getPath();
            BufferedImage fruitsBufferedImage = ImageIO.read(Paths.get(fruitsPath).toFile());
            Iterator<ImageReader> fruitsReaders = ImageIO.getImageReaders(bufferedImage);
            List<IIOMetadata> fruitsMetadata = new ArrayList<>();
            while (fruitsReaders.hasNext()) {
                fruitsMetadata.add(fruitsReaders.next().getStreamMetadata());
            }
            assertEquals(imageMetadata, fruitsMetadata);
            assertNotNull(fruits);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void when_findAll_by_service_than_findAll_by_repository() {
        List<ImageDescription> repositoryAll = imageRepository.findAll();
        List<ImageDescription> serviceAll = imageService.findAll();
        assertEquals(repositoryAll, serviceAll);
    }

    @Test
    public void when_delete_in_database_than_null() {
        try {
            byte[] bytes = Files.readAllBytes(path);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
            ImageDescription savedImageDescription = imageService.saveImage(imageInputStream, "fruits");
            imageService.deleteImageInDb(savedImageDescription.getId());
            ImageDescription deletedImage = imageRepository.getOne(savedImageDescription.getId());
            assertNull(deletedImage);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}