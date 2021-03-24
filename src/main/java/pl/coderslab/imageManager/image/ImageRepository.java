package pl.coderslab.imageManager.image;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.imageManager.image.Image;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    public Image findOneByName(String name);

}
