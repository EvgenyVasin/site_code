package ru.jskills.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.jskills.entities.*;
import ru.jskills.repositories.CoursesRepository;
import ru.jskills.repositories.TopicsRepository;
import ru.jskills.repositories.PagesRepository;
import ru.jskills.repositories.ParagraphsRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by safin.v on 28.10.2016.
 */

@Controller
//@RequestMapping("/courses")
public class CoursesController {

    public static final int ADD_COURSE = 0;
    public static final int ADD_TOPIC = 1;
    public static final int ADD_PARAGRAPH = 2;

    @Autowired
    CoursesRepository courses;
    @Autowired
    TopicsRepository topics;
    @Autowired
    PagesRepository pages;
    @Autowired
    ParagraphsRepository paragraphs;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    private String viewCourse(Course course,  Model model){
        model.addAttribute("course", course);
        model.addAttribute("lectureList", topics.findByCourse(course));
        return "courses/course";
    }

    private String viewTopic(Topic topic, Model model, Page page){
        model.addAttribute("course", topic.getCourse());
        model.addAttribute("topic", topic);
        model.addAttribute("current_page", page);
        model.addAttribute("paragraphList", paragraphs.findByPage(page, new Sort(Sort.Direction.ASC, "number")));
        model.addAttribute("pageList", pages.findByTopic(topic, new Sort(Sort.Direction.ASC, "number")));
        return "courses/topic";
    }

    private String viewCourses(Model model){
        model.addAttribute("courseList", courses.findAll(new Sort(Sort.Direction.ASC, "number")));
        return "courses/courses";
    }

    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public String getCourses(Model model)
    {
        return  viewCourses(model);
    }

    @RequestMapping(value = "/course/{courseId}", method = RequestMethod.GET)
    public String getCourse(@PathVariable Long courseId, Model model)
    {   Course course = courses.findOne(courseId);
        return viewCourse(course, model);

    }

    @RequestMapping(value = "/topic/{topicId}/{currentPageNumber}", method = RequestMethod.GET)
    public String getTopic(@PathVariable Long topicId, @PathVariable Long currentPageNumber, Model model)
    {   Topic topic = topics.findOne(topicId);
        Page page = pages.findByNumberAndTopic(currentPageNumber, topic);
        return viewTopic(topic, model, page);

    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delete_course/{courseId}", method = RequestMethod.GET)
    public String deleteCourse(@PathVariable Long courseId, Model model){
        courses.delete(courseId);
        return viewCourses(model);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delete_topic/{topicId}", method = RequestMethod.GET)
    public String deleteLecture(@PathVariable Long topicId, Model model){
        Topic topic = topics.findOne(topicId);
        Course course = topic.getCourse();
        topics.delete(topicId);
        return viewCourse(course, model);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delete_paragraph/{paragraphId}", method = RequestMethod.GET)
    public String deleteParagraph(@PathVariable Long paragraphId, Model model){
        Paragraph paragraph = paragraphs.findOne(paragraphId);
        Page page = paragraph.getPage();
        paragraphs.delete(paragraphId);
        return viewTopic(page.getTopic(), model, page);
    }

    private void innerAddCourses(CustomCourses detal, MultipartFile img, Long number, String caption, String description){
        detal.setNumber(number);
        detal.setCaption(caption);
        detal.setText(description);
        detal.setDateTime(new Date());

        if (!img.isEmpty()) {
            try {
                File folder = new File("pictures");

                if (!folder.exists()) {
                    folder.mkdir();
                }

                byte[] fileBytes = img.getBytes();

                String rootPath = "pictures/";
                System.out.println("File content type: " + img.getContentType());
                File newFile = new File(rootPath + img.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile));
                stream.write(fileBytes);
                stream.close();

                detal.setImgLink(rootPath + img.getOriginalFilename());

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("File upload is failed: " + e.getMessage());
            }
        }
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/coursesadd/{actId}/{parentId}", method = RequestMethod.GET)
    public String getFormAdd(@PathVariable Long actId, @PathVariable Long parentId, Model model){
        model.addAttribute("actId", actId);
        model.addAttribute("parentId", parentId);
        return "courses/add_courses";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/addNewPage/{currentPageId}", method = RequestMethod.GET)
    public  String addNewPage(@PathVariable Long currentPageId, Model model){
        Page currentPage = pages.findOne(currentPageId);

        Topic topic = currentPage.getTopic();
        Long curPageNumber = currentPage.getNumber();

        List<Page> pageList = pages.findByTopic(topic);
        for (Page pg:pageList) {
            Long pgNumber = pg.getNumber();
            if (pgNumber >  curPageNumber)
                pg.setNumber(pgNumber + 1);
        }
        pages.save(pageList);

        Page page = new Page();
        page.setNumber(curPageNumber + 1);
        page.setTopic(topic);
        pages.save(page);
        return viewTopic(topic, model, page);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/deletePage/{currentPageId}", method = RequestMethod.GET)
    public  String deletePage(@PathVariable Long currentPageId, Model model){
        Page currentPage = pages.findOne(currentPageId);

        Topic topic = currentPage.getTopic();
        Long curPageNumber = currentPage.getNumber();

        List<Page> pageList = pages.findByTopic(topic);
        if (pageList.size()==1)
            return viewTopic(topic, model, currentPage);

        pages.delete(currentPageId);

        pageList = pages.findByTopic(topic);

        for (Page pg:pageList) {
            Long pgNumber = pg.getNumber();
            if (pgNumber >  curPageNumber)
                pg.setNumber(pgNumber - 1);
        }
        pages.save(pageList);

        if(curPageNumber > 1)
            curPageNumber = curPageNumber - 1;
        Page page = pages.findByNumberAndTopic(curPageNumber, topic);

        return viewTopic(topic, model, page);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/add_courses", method = RequestMethod.POST)
    public  String add(HttpServletRequest request, Model model, @RequestParam("img")MultipartFile img, Integer actId, Long parentId, Long number, String caption, String description)
    {
        switch (actId){
            case ADD_COURSE:
                Course course = new Course();
                innerAddCourses(course, img, number, caption, description);
                courses.save((Course)course);
                return viewCourse((Course)course, model);


            case ADD_TOPIC:
                Course parentCourse = courses.findOne(parentId);

                Topic topic = new Topic();
                innerAddCourses(topic, img, number, caption, description);
                topic.setCourse(parentCourse);
                topics.save(topic);

                Page page = new Page();
                page.setNumber(new Long(1));
                page.setTopic(topic);
                pages.save(page);
                return viewTopic(topic, model, page);

            case ADD_PARAGRAPH:
                Page parentPage = pages.findOne(parentId);
                Paragraph paragraph= new Paragraph();
                innerAddCourses(paragraph, img, number, caption, description);
                paragraph.setPage(parentPage);
                paragraphs.save(paragraph);
                return viewTopic(parentPage.getTopic(),model, parentPage);
        }



        return null;

    }
}
