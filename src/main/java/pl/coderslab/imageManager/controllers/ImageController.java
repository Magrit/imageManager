package pl.coderslab.imageManager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.imageManager.model.ImageDescription;
import pl.coderslab.imageManager.services.ImageService;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping("/")
    public String homePage(Model model) {
        List<ImageDescription> imagesList = imageService.findAll();
        model.addAttribute("imagesList", imagesList);
        return "home";
    }

    @GetMapping("/upload")
    public String formPage() {
        return "form";
    }

    @PostMapping("/upload")
    public String performForm(@RequestParam("image") MultipartFile imagePart, @RequestParam("name") String name) {
        try {
            InputStream inputStream = imagePart.getInputStream();
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
            boolean isSaved = imageService.saveImage(imageInputStream, name);
            if (isSaved) {
                return "redirect:/";
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return "redirect:/upload";
    }

    @RequestMapping("/delete/{id}")
    public String deleteImage(@PathVariable long id) {
        try {
            imageService.deleteImage(id);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        imageService.deleteImageInDb(id);
        return "redirect:/";
    }
}
