package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    void update(User user);

    void delete(long id);

    User findUserById(long id);

    void save(UserDTO userDTO);

    User findByUserName(String username);

    void update(Long id, UserDTO userDTO);

}
