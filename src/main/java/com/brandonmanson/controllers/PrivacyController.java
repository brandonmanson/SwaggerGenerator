package com.brandonmanson.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by brandonmanson on 3/29/17.
 */
@Controller
public class PrivacyController {

    @RequestMapping("/privacy")
    public String showPrivacyPolicy() {
        return "privacy";
    }
}
