package ru.jskills.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by safin.v on 29.09.2016.
 */
@Entity
@Table(name = "courses")
public class Course extends CustomCourses{
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Topic> topics = new HashSet<Topic>();

    @Override
    public String toString() {
        return "Course{" +
                "caption='" + getCaption() + '\'' +
                ", description='" + getText() + '\'' +
                '}';
    }
}
