package ru.jskills.entities;

import javax.persistence.*;

/**
 * Created by safin.v on 17.11.2016.
 */
@MappedSuperclass
public class CustomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "img_link", length = 256)
    private String imgLink;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
