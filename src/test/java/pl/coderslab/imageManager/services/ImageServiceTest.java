package pl.coderslab.imageManager.services;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import pl.coderslab.imageManager.repositories.ImageRepository;

import static org.mockito.Mockito.mock;

class ImageServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ImageServiceTest.class);
    private ImageService imageService;
    private ImageRepository imageRepository;

    @Before
    public void setUp() {
        this.imageRepository = mock(ImageRepository.class);
        this.imageService = new ImageService(imageRepository);
    }

    @Test
    void saveImage() {
    }

    @Test
    void findAll() {
    }

    @Test
    void deleteImageInDb() {
    }

    @Test
    void deleteImage() {
    }
}