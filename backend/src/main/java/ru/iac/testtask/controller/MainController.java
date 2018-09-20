package ru.iac.testtask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Roman
 */
@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("/")
    public String index(Model model) {
        return "main/index";
    }

}
