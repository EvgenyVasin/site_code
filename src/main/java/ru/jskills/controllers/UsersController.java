package ru.jskills.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.jskills.entities.User;
import ru.jskills.entities.UserRole;
import ru.jskills.repositories.UserRoleRepository;
import ru.jskills.repositories.UsersRepository;

import java.util.*;

/**
 * Created by safin.v on 26.10.2016.
 */
@RestController
@RequestMapping("/users")

public class UsersController {
    @Autowired
    UsersRepository users;
    @Autowired
    BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    UserRoleRepository roles;
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers()
    {
        List<User> result = new ArrayList<>();
        users.findAll().forEach(result::add);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST)
    public  ModelAndView addUser(String username, String password, String password_confirm, String e_mail, Model model)
    {
        //no empty fields allowed
        if (username.isEmpty() || password.isEmpty() || password_confirm.isEmpty())
            return null;
        //passwords should match
        if (!password.equals(password_confirm))
            return null;
        UserRole userRole = roles.findByUserRoleName("ROLE_USER");
        User user = new User();
        user.setUsername(username);
        user.setPassword(bcryptEncoder.encode(password));
        user.setFirstName(" ");
        user.setLastName(" ");
        user.setMail(e_mail);
        user.setUserRole(userRole);
        user.setDateRegistration(new Date());
        user.setEnabled(true);
        users.save(user);
        Map<String,String> attribute = new HashMap<>();
        attribute.put("msg_info", "регистрация завершена");
        return new ModelAndView("info",attribute);
    }


    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public ModelAndView getUserForm()
    {
        return new ModelAndView("add");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id)
    {
        users.delete(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable("id") Long id)
    {
        return users.findOne(id);
    }
}