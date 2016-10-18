package ru.jskills.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by safin.v on 18.10.2016.
 */
@Controller
public class ValidatorController {
    @RequestMapping(value = "/test_validator", method = RequestMethod.GET)
    public String validator(){
        return "test_validator";
    }
}
