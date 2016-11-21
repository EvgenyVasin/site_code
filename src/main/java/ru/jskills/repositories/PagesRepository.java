package ru.jskills.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import ru.jskills.entities.Page;
import ru.jskills.entities.Topic;

import java.util.List;

/**
 * Created by safin.v on 07.11.2016.
 */
public interface PagesRepository extends CrudRepository<Page, Long> {
    Page findByNumber(Long number);
    Page findByNumberAndTopic(Long number, Topic topic);
    List<Page> findByTopic(Topic topic);
    List<Page> findByTopic(Topic topic, Sort sort);
    List<Page> findAll(Sort sort);

}
