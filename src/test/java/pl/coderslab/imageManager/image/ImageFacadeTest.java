package pl.coderslab.imageManager.image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pl.coderslab.imageManager.mock.MockDatabase;

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

class ImageFacadeTest {

    private ImageFacade imageFacade;
    private ImageRepository imageRepository;
    private static final String ABSOLUTE_IMAGE_PATH = "src/main/webapp/";

    private final Path path = Paths.get("src/main/webapp/images/0/fruits.jpeg");
    private final File file = path.toFile();

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setUp() {
        imageRepository = new MockDatabase();
        imageFacade = new ImageFacade(imageRepository);
    }

    @Test
    public void when_saving_image_return_image() throws IOException {
        //given
        BufferedImage bufferedImage = ImageIO.read(file);
        byte[] bytes = Files.readAllBytes(path);
        InputStream inputStream = new ByteArrayInputStream(bytes);
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
        //than
        imageFacade.saveImage(imageInputStream, "fruits");
        Image fruits = imageRepository.getByName("fruits");
        //should
        String fruitsPath = ABSOLUTE_IMAGE_PATH + fruits.getPath();
        BufferedImage fruitsBufferedImage = ImageIO.read(Paths.get(fruitsPath).toFile());
        assertNotNull(fruits);
        assertEquals(getMetadata(bufferedImage), getMetadata(fruitsBufferedImage));
    }

    @Test
    public void when_delete_in_database_than_null() throws IOException {
        //given
        byte[] bytes = Files.readAllBytes(path);
        InputStream inputStream = new ByteArrayInputStream(bytes);
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
        imageFacade.saveImage(imageInputStream, "fruits");
        //than
        Image savedImage = imageRepository.getByName("fruits");
        imageFacade.deleteImage(savedImage.getId());
        //should
        Image deletedImage = imageRepository.getOne(savedImage.getId());
        assertNull(deletedImage);
    }

    private List<IIOMetadata> getMetadata(BufferedImage bufferedImage) throws IOException {
        Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(bufferedImage);
        List<IIOMetadata> imageMetadata = new ArrayList<>();
        while (imageReaders.hasNext()) {
            imageMetadata.add(imageReaders.next().getStreamMetadata());
        }
        return imageMetadata;
    }

}