package ru.jskills.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ru.jskills.entities.Course;
import ru.jskills.repositories.CoursesRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by safin.v on 28.10.2016.
 */

@Controller
@RequestMapping("/courses")
public class CoursesController {

    @Autowired
    CoursesRepository courses;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    @RequestMapping("/courses")
//    public String main(){
//        logger.debug("This is a debug message");
//        logger.info("This is an info message");
//        logger.warn("This is a warn message");
//        logger.error("This is an error message");
//        return "courses/courses";
//    }

    @RequestMapping(method = RequestMethod.GET)
    public String getCourses(Model model)
    {
        model.addAttribute("courseList", courses.findAll());
        return "courses/courses";
    }

    @RequestMapping(method = RequestMethod.POST)
    public  String addCourse(HttpServletRequest request, Model model, @RequestParam("img")MultipartFile img, String caption, String description)
    {
        //no empty fields allowed
        if (caption.isEmpty() || description.isEmpty())
            return null;

        Course course = new Course();
        course.setCaption(caption);
        course.setDescription(description);

        if (!img.isEmpty()) {
            try {
                byte[] fileBytes = img.getBytes();
//                String rootPath = System.getProperty("user.dir");
            String rootPath = System.getProperty("user.home");
                String uploadDir = request.getContextPath();
                System.out.println("Server rootPath: " + rootPath);
                System.out.println("File original name: " + img.getOriginalFilename());
                System.out.println("File content type: " + img.getContentType());
                File newFile = new File(img.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile));
                stream.write(fileBytes);
                stream.close();
                System.out.println("File is saved under: " + img.getOriginalFilename());

                course.setImgLink(img.getOriginalFilename());

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("File upload is failed: " + e.getMessage());
            }
        }




        courses.save(course);

        model.addAttribute("courseList", courses.findAll());
        return "courses/courses";
    }
}
