package ru.jskills.entities;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by safin.v on 29.09.2016.
 */
@Entity
@Table(name = "paragraphs")
public class Paragraph  extends CustomCourses{


    @ManyToOne
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;



    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }


    @Override
    public String toString() {
        return "Paragraph{" +
                "lecture=" + lecture +
                ", caption='" + getCaption() + '\'' +
                ", text='" + getText() + '\'' +
                '}';
    }
}
