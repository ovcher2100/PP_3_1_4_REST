package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;


import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.util.*;



@Controller
@RequestMapping("/api/admin")
public class AdministratorController {

    private final UserService userService;
    private final RoleService roleService;


    public AdministratorController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> adminPage(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = (User) userService.loadUserByUsername(username);
        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("username", username);
        response.put("roles", user.getRoles());
        response.put("email", user.getEmail());
        response.put("users", userService.getAllUsers());
        response.put("allRole", roleService.getAllRoles());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addUser(@RequestBody User user) {
        Role role = roleService.findRoleByName(user.getRoles().iterator().next().getName());
        if (role != null) {
            user.getRoles().clear();
            user.getRoles().add(role);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
        userService.saveUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}/delete")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        User user = userService.findUserById(id);
        userService.delete(user.getId());
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PutMapping("/user/{id}/edit")
    public ResponseEntity<HttpStatus> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        user.setId(id);
        userService.update(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
