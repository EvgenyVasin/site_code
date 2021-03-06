package ru.jskills.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.jskills.entities.Course;

import java.util.List;

/**
 * Created by safin.v on 28.10.2016.
 */
@Repository
@Qualifier(value = "coursesRepository")
public interface CoursesRepository extends CrudRepository<Course, Long> {
    List<Course> findAll(Sort sort);
}
