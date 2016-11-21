package ru.jskills.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.jskills.repositories.CommentsRepository;

/**
 * Created by safin.v on 21.11.2016.
 */
@Controller
public class IndexController {
    @Autowired
    CommentsRepository comments;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndex(Model model){
        model.addAttribute("commentList", comments.findAll(new Sort(Sort.Direction.DESC, "dateTime")));
        return "index";
    }
}
