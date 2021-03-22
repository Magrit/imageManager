package pl.coderslab.imageManager.image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.coderslab.imageManager.mock.MockDatabase;

import static org.mockito.Mockito.mock;

class ImageControllerTest {
    private static ImageController imageController;
    private static final String HOME_PAGE_VIEW_NAME = "home";
    private static final String FORM_PAGE_VIEW_NAME = "form";
    private static ImageFacade imageFacade;
    private static ImageRepository imageRepository;

    @BeforeEach
    public void setUp() {
        imageRepository = new MockDatabase();
        imageFacade = new ImageFacade(imageRepository);
        imageController = new ImageController(imageFacade, imageRepository);
    }

    @Test
    public void should_return_home_page_when_home_action_called() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/image"))
                .andExpect(MockMvcResultMatchers.view().name(HOME_PAGE_VIEW_NAME));
    }

    @Test
    public void should_return_form_when_upload_called() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/image/upload"))
                .andExpect(MockMvcResultMatchers.view().name(FORM_PAGE_VIEW_NAME));
    }

}