package ru.jskills.controllers;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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
import ru.jskills.entities.Lecture;
import ru.jskills.repositories.CoursesRepository;
import ru.jskills.repositories.LecturesRepository;

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
    LecturesRepository lectures;

    @RequestMapping(value = "/imgCourse={curseId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getCourseImg(@PathVariable(value = "curseId") String curseId) throws IOException {
        Course course = courses.findOne(Long.parseLong(curseId));

        ByteArrayOutputStream out = null;
        InputStream input = null;
        try{
            out = new ByteArrayOutputStream();
            input = new BufferedInputStream(new FileInputStream(course.getImgLink()));
            int data = 0;
            while ((data = input.read()) != -1){
                out.write(data);
            }
        }
        finally{
            if (null != input){
                input.close();
            }
            if (null != out){
                out.close();
            }
        }
        byte[] bytes = out.toByteArray();

        final HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.parseMediaType(course.getImgContentType()));

        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/imgLecture={lectureId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getLectureImg(@PathVariable(value = "lectureId") String lectureId) throws IOException {
        Lecture lecture = lectures.findOne(Long.parseLong(lectureId));

        ByteArrayOutputStream out = null;
        InputStream input = null;
        try {
            out = new ByteArrayOutputStream();
            input = new BufferedInputStream(new FileInputStream(lecture.getImgLink()));
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

        headers.setContentType(MediaType.parseMediaType(lecture.getImgContentType()));

        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
    }
}
