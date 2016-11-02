package ru.jskills.entities;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by safin.v on 29.09.2016.
 */
@Entity
@Table(name = "lectures")
public class Lecture extends CustomCourses{

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
        private Course course;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Paragraph> paragraphs = new HashSet<Paragraph>();

    public Set<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(Set<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }



    @Override
    public String toString() {
        return "Lecture{" +
                "course=" + course +
                ", caption='" + getCaption() + '\'' +
                ", description='" + getText() + '\'' +
                '}';
    }
}
