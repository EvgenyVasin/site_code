package ru.jskills.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by safin.v on 20.10.2016.
 */
@Controller
public class RunTimeExceptionController {
    @RequestMapping("/nnn")
    Exception index(){


        return new RuntimeException("dsfadsgasdgdsagfdsagdsgdsagdsagdsdsagdsgdsagdstgrewtvfdagdav");
    }
}
