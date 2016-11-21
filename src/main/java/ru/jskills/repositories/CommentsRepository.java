package ru.jskills.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.jskills.entities.Comment;

import java.util.List;

/**
 * Created by safin.v on 21.11.2016.
 */
@Repository
@Qualifier(value = "commentsRepository")
public interface CommentsRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAll(Sort sort);
}
