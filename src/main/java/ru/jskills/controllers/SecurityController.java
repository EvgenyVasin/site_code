package ru.jskills.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by safin.v on 21.10.2016.
 */
@Controller
public class SecurityController {
    //Spring Security
    @Secured(value={"ROLE_ADMIN"})
    @RequestMapping(value="/adminMethodSecured", method= RequestMethod.GET)
    public ModelAndView adminMethodSecured(HttpSession session) {
        System.out.println("SecurityController adminMethodSecured() is called with ADMIN ROLE");
        session.setMaxInactiveInterval(3600);
        session.setAttribute("sessionObject", "ROLE_ADMIN");
        return new ModelAndView("/security/admin");

    }

    //Spring Security
    @Secured(value={"ROLE_USER"})
    @RequestMapping(value="/userMethodSecured", method= RequestMethod.GET)
    public ModelAndView userMethodSecured(HttpSession session) {
        System.out.println("SecurityController userMethodSecured() is called with USER ROLE");
        session.setMaxInactiveInterval(3600);
        session.setAttribute("sessionObject", "ROLE_USER");
        return new ModelAndView("/security/admin");

    }
}
