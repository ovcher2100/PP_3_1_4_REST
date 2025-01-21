package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserService extends UserDetailsService {
    List<User> getAllUsers();
    void add(User user);
    void update(User user);
    void delete(long id);
    User findUserById(long id);
    void saveUser(User user);
    User findByUserName(String username);
    void updateUserFromDTO(Long id, UserDTO userDTO);

}
