package ru.jskills.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.jskills.entities.Page;
import ru.jskills.entities.Paragraph;

import java.util.List;

/**
 * Created by safin.v on 03.11.2016.
 */
@Repository
@Qualifier(value = "paragraphsRepository")
public interface ParagraphsRepository extends CrudRepository<Paragraph, Long> {
    List<Paragraph> findByPage(Page page);
}
