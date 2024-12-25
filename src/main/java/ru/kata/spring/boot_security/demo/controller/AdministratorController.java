package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdministratorController {

    private final UserService userService;
    private final RoleService roleService;


    public AdministratorController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String adminPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();
        User user = (User) userService.loadUserByUsername(username);
        model.addAttribute("username", username);
        model.addAttribute("roles", user.getRoles());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("allRole", roleService.getAllRoles());
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("newUser") User user, @RequestParam("roles") List<Long> roleIds) {
        List<Role> roles = roleService.getRolesById(roleIds);
        user.setRoles(roles);
        userService.saveUser(user, roles);
        return "redirect:/admin";
    }
//    @PostMapping("/add")
//    public String addUser(@ModelAttribute("newUser") User user,
//                          @RequestParam("roles") List<Long> roleIds) {
//        try {
//            List<Role> roles = roleService.getRolesById(roleIds);
//            if (roles.isEmpty()) {
//                throw new IllegalArgumentException("No roles found for the provided IDs");
//            }
//            user.setRoles(roles);
//
//            userService.saveUser(user, roles);
//
//            return "redirect:/admin";
//        } catch (Exception e) {
//            // Логируем исключение для отладки
//            System.err.println("Error adding user: " + e.getMessage());
//            e.printStackTrace(); // Печатаем стек вызовов
//        }
//        return "/";
//    }


    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("allRole", roleService.getAllRoles());
        return "edit_user";
    }
    @PostMapping("/update_user")
    public String updateUser(@ModelAttribute User user,@RequestParam Long id){
        user.setId(id);
        userService.update(user);
        return "redirect:/admin";
    }
}
