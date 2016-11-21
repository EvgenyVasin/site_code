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
@Table(name = "topics")
public class Topic extends CustomCourses{

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Page> pages = new HashSet<Page>();


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }



    @Override
    public String toString() {
        return "Topic{" +
                "course=" + course +
                ", caption='" + getCaption() + '\'' +
                ", description='" + getText() + '\'' +
                '}';
    }
}
