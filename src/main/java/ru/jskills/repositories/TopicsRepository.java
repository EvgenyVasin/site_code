package ru.jskills.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.jskills.entities.Course;
import ru.jskills.entities.Topic;

import java.util.List;

/**
 * Created by safin.v on 01.11.2016.
 */
@Repository
@Qualifier(value = "lecturesRepository")
public interface TopicsRepository extends CrudRepository<Topic, Long> {
    List<Topic> findByCourse(Course course);
    List<Topic> findAll(Sort sort);
}

