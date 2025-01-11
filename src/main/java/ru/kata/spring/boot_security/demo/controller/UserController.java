package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;


@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/user")
    public User showUserPage(@AuthenticationPrincipal User user) {
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }
}
