package ru.jskills.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by safin.v on 15.11.2016.
 */
@Controller
public class ValidatorController {
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @RequestMapping(value = "/test_validator", method = RequestMethod.GET)
    public String getValidator(){
        return "courses/test_validator";
    }

}
