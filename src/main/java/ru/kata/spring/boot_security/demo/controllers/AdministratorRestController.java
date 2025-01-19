package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserConverter;
import ru.kata.spring.boot_security.demo.services.UserService;


import java.security.Principal;
import java.util.*;



@RestController
@RequestMapping("/api")
public class AdministratorRestController {

    private final UserService userService;
    private final RoleService roleService;


    public AdministratorRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        User user = UserConverter.toUser(userDTO, roleService);
        userService.saveUser(user);
        return ResponseEntity.ok(UserConverter.toUserDTO(user));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        User user = userService.findUserById(id);
        userService.delete(user.getId());
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PutMapping("/user/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User user = UserConverter.toUser(userDTO, roleService);
        user.setId(id);
        userService.update(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrent(Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
