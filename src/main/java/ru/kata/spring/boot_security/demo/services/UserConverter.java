package ru.kata.spring.boot_security.demo.services;


import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    private final RoleService roleService;

    public UserConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    public  UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(user.getRoles().stream()
                .map(Role::getId)  // Извлекаем только ID ролей
                .collect(Collectors.toSet()));
        return userDTO;
    }

    public  User toUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        List<Long> roleIds = new ArrayList<>(userDTO.getRoles());
        Set<Role> roles = roleService.findByIds(roleIds);

        user.setRoles(roles);
        return user;
    }
}
