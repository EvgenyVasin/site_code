package ru.jskills.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by safin.v on 01.11.2016.
 */
@MappedSuperclass
public abstract class CustomCourses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "number", nullable = false)
    private Long number;

    @Column(name = "img_link", length = 256)
    private String imgLink;

    @Column(name = "img_content_type", length = 256)
    private String imgContentType;

    @Column(name = "caption", length = 256, nullable = false)
    private String caption;

    @Column(name = "text", length = 1024)
    private String text;

    @Column(name = "date_time")
    private Date dateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getImgContentType() {
        return imgContentType;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
