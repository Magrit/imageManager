package pl.coderslab.imageManager.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.coderslab.imageManager.repositories.ImageRepository;
import pl.coderslab.imageManager.services.ImageService;

import static org.mockito.Mockito.mock;

class ImageControllerTest {
    private static ImageController imageController;
    private static String HOME_PAGE_VIEW_NAME = "home";
    private static String FORM_PAGE_VIEW_NAME = "form";
    private ImageService imageService;

    @BeforeEach
    public void beforeClass() {
        imageService = new ImageService(mock(ImageRepository.class));
        imageController = new ImageController(imageService);
    }

    @Test
    public void test_home_action_return_home() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.view().name(HOME_PAGE_VIEW_NAME));
    }

    @Test
    public void test_upload_action_return_form() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/upload"))
                .andExpect(MockMvcResultMatchers.view().name(FORM_PAGE_VIEW_NAME));
    }

}