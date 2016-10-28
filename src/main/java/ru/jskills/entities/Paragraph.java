package ru.jskills.entities;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by safin.v on 29.09.2016.
 */
@Entity
@Table(name = "paragraphs")
public class Paragraph {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Column(name = "img_link", length = 256)
    private String imgLink;

    @Column(name = "caption", length = 256, nullable = false)
    private String caption;

    @Column(name = "paragraph_text", length = 1024)
    private String text;

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

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
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

    @Override
    public String toString() {
        return "Paragraph{" +
                "lecture=" + lecture +
                ", caption='" + caption + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
