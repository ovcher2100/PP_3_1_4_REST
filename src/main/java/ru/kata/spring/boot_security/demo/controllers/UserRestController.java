package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserService;


@RestController
@RequestMapping("/api")
public class UserRestController {

    private final UserService userService;


    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/current")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUserName(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }

}
