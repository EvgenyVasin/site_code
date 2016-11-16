package ru.jskills.controllers;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.jskills.entities.Course;
import ru.jskills.entities.CustomCourses;
import ru.jskills.entities.Topic;
import ru.jskills.entities.Paragraph;
import ru.jskills.repositories.CoursesRepository;
import ru.jskills.repositories.TopicsRepository;
import ru.jskills.repositories.ParagraphsRepository;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by safin.v on 01.11.2016.
 */
@Controller
@ResponseBody
public class ImageController {
    @Autowired
    CoursesRepository courses;
    @Autowired
    TopicsRepository lectures;
    @Autowired
    ParagraphsRepository paragraphs;

    @RequestMapping(value = "/imgCourse={curseId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getCourseImg(@PathVariable(value = "curseId") Long  curseId) throws IOException {
        Course course = courses.findOne(curseId);
        return getImg(course);
    }

    @RequestMapping(value = "/imgTopic={topicId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getTopicImg(@PathVariable(value = "topicId") Long  topicId) throws IOException {
        Topic topic = lectures.findOne(topicId);

        return getImg(topic);
    }

    @RequestMapping(value = "/imgParagraph={paragraphId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getParagraphImg(@PathVariable(value = "paragraphId") Long paragraphId)  throws IOException{
        Paragraph paragraph =paragraphs.findOne(paragraphId);

        return getImg(paragraph);
    }

    private ResponseEntity<byte[]> getImg(CustomCourses detal) throws IOException{
        ByteArrayOutputStream out = null;
        InputStream input = null;
        try {
            out = new ByteArrayOutputStream();
            input = new BufferedInputStream(new FileInputStream(detal.getImgLink()));
            int data = 0;
            while ((data = input.read()) != -1) {
                out.write(data);
            }
        } finally {
            if (null != input) {
                input.close();
            }
            if (null != out) {
                out.close();
            }
        }
        byte[] bytes = out.toByteArray();

        final HttpHeaders headers = new HttpHeaders();

        //headers.setContentType(MediaType.parseMediaType(detal.getImgContentType()));

        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
    }
}
