package pl.coderslab.imageManager.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.imageManager.exeption.ImageSavingException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/image")
@RequiredArgsConstructor
class ImageController {

    private final ImageFacade imageFacade;
    private final ImageRepository imageRepository;

    @GetMapping
    public String homePage(Model model) {
        List<Image> imagesList = imageRepository.findAll();
        model.addAttribute("imagesList", imagesList);
        return "home";
    }

    @GetMapping("/upload")
    public String formPage() {
        return "form";
    }

    @PostMapping("/upload")
    public String uploadForm(@RequestParam("image") MultipartFile imagePart, @RequestParam("name") String name) {
        try {
            InputStream inputStream = imagePart.getInputStream();
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
            imageFacade.saveImage(imageInputStream, name);
        } catch (IOException | ImageSavingException exception) {
            return "redirect:/error";
        }
        return "redirect:/image";
    }

    @DeleteMapping("/{id}")
    public String deleteImage(@PathVariable long id) {
        try {
            imageFacade.deleteImage(id);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return "redirect:/image";
    }
}
