package com.bulke.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping(value = "/login")
    public String prepareLogin(Model model) {

        model.addAttribute("globalMenu", "login");

        return "login/login";
    }

}
