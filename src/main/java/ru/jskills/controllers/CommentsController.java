package ru.jskills.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.jskills.entities.Comment;
import ru.jskills.entities.User;
import ru.jskills.repositories.CommentsRepository;
import ru.jskills.repositories.UsersRepository;

import java.util.Date;

/**
 * Created by safin.v on 21.11.2016.
 */
@Controller
public class CommentsController {
    @Autowired
    CommentsRepository comments;
    @Autowired
    UsersRepository users;

    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @RequestMapping(value = "/addCommentForm", method = RequestMethod.GET)
    public String addCommentForm(){
        return "/users/add_comment";
    }


    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @RequestMapping(value = "/addNewComment", method = RequestMethod.POST)
    public String addCommentForm(Model model, String userName, String commentMsg){
        User user = users.findByUsername(userName);
        Comment comment = new Comment();
        comment.setDateTime(new Date());
        comment.setUser(user);
        comment.setMsg(commentMsg);
        comments.save(comment);

        model.addAttribute("commentList", comments.findAll(new Sort(Sort.Direction.DESC, "dateTime")));
        return "index";
    }

}
