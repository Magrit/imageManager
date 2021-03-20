package pl.coderslab.imageManager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Setter
@Getter
public class ImageDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String path;
    private String minPath;
    private String format;
    private int height;
    private int width;
}
