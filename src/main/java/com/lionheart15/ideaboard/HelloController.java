package com.lionheart15.ideaboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/develop")
    public String develop() {
        return "hello";
    }

    @GetMapping("/login")
    public String login() {
        return "hello";
    }
}
