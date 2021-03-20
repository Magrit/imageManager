package pl.coderslab.imageManager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.imageManager.model.ImageDescription;

public interface ImageRepository extends JpaRepository<ImageDescription, Long> {

}
