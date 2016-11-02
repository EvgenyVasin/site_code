package ru.jskills.entities;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by safin.v on 29.09.2016.
 */
@Entity
@Table(name = "courses")
public class Course extends CustomCourses{
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Lecture> lectures = new HashSet<Lecture>();

    public Set<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(Set<Lecture> lectures) {
        this.lectures = lectures;
    }

    @Override
    public String toString() {
        return "Course{" +
                "caption='" + getCaption() + '\'' +
                ", description='" + getText() + '\'' +
                '}';
    }
}
