package ru.jskills.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.jskills.entities.Course;
import ru.jskills.entities.CustomCourses;
import ru.jskills.entities.Lecture;
import ru.jskills.repositories.CoursesRepository;
import ru.jskills.repositories.LecturesRepository;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * Created by safin.v on 28.10.2016.
 */

@Controller
//@RequestMapping("/courses")
public class CoursesController {

    public static final int ADD_COURSE = 0;
    public static final int ADD_LECTURE = 1;
    public static final int ADD_PARAGRAPH = 2;

    @Autowired
    CoursesRepository courses;
    @Autowired
    LecturesRepository lectures;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    private String viewCourse(Course course,  Model model){
        model.addAttribute("course", course);
        model.addAttribute("lectureList", lectures.findByCourse(course));
        return "courses/course";
    }

    private String viewCourses(Model model){
        model.addAttribute("courseList", courses.findAll());
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

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delete_course/{courseId}", method = RequestMethod.GET)
    public String deleteCourse(@PathVariable Long courseId, Model model){
        courses.delete(courseId);
        return viewCourses(model);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delete_lecture/{lectureId}", method = RequestMethod.GET)
    public String deleteLecture(@PathVariable Long lectureId, Model model){
        Lecture lecture = lectures.findOne(lectureId);
        Course course = lecture.getCourse();
        lectures.delete(lectureId);
        return viewCourse(course, model);
    }

    private void innerAddCourses(CustomCourses course, MultipartFile img, Long number, String caption, String description){
        course.setNumber(number);
        course.setCaption(caption);
        course.setText(description);
        course.setDateTime(new Date());

        if (!img.isEmpty()) {
            try {
                File folder = new File("pictures");

                if (!folder.exists()) {
                    folder.mkdir();
                }

                byte[] fileBytes = img.getBytes();

                String rootPath = "pictures/";

                course.setImgContentType(img.getContentType());
                System.out.println("File content type: " + img.getContentType());
                File newFile = new File(rootPath + img.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile));
                stream.write(fileBytes);
                stream.close();

                course.setImgLink(rootPath + img.getOriginalFilename());

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("File upload is failed: " + e.getMessage());
            }
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/coursesadd/{actId}/{parentId}", method = RequestMethod.GET)
    public String getFormAdd(@PathVariable Long actId, @PathVariable Long parentId, Model model){
        model.addAttribute("actId", actId);
        model.addAttribute("parentId", parentId);
        return "courses/add_courses";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/add_courses", method = RequestMethod.POST)
    public  String add(HttpServletRequest request, Model model, @RequestParam("img")MultipartFile img, Integer actId, Long parentId, Long number, String caption, String description)
    {

        if (caption.isEmpty() || description.isEmpty())
            return null;

        switch (actId){
            case ADD_COURSE:
                Course course = new Course();
                innerAddCourses(course, img, number, caption, description);
                courses.save((Course)course);
                return viewCourse((Course)course, model);


            case ADD_LECTURE:
                Course parentCourse = courses.findOne(parentId);
                Lecture lecture = new Lecture();
                innerAddCourses(lecture, img, number, caption, description);
                ((Lecture)lecture).setCourse(parentCourse);
                lectures.save((Lecture)lecture);
                return viewCourse(parentCourse, model);
        }



        return null;

    }
}
