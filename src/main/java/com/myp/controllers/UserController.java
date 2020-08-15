package com.myp.controllers;

import com.myp.sections.User.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    UserService service =  new UserService();

    @RequestMapping(value = "api/v1.0/testAPI",method = RequestMethod.GET)
    public Object testMethod() {return "Hello Sunshine ~ Happy 2020～";}

    @RequestMapping(value = "api/v1.0/testLogAPI",method = RequestMethod.GET)
    public Object testMethodTwo() {return service.testLogMethod();}


}
