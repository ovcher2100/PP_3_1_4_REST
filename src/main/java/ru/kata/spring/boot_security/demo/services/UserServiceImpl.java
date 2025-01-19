package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.configs.PasswordEncoderConfig;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;


import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoder;

    public UserServiceImpl(PasswordEncoderConfig passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void add(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(User updateUser) {
        User user = findUserById(updateUser.getId());
        if (user == null) {
            throw new RuntimeException("User not found with id: " + updateUser.getId());
        }
        user.setUsername(updateUser.getUsername());
        user.setEmail(updateUser.getEmail());
        // Обновление пароля только если он передан и не пуст
        if (updateUser.getPassword() != null && !updateUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.passwordEncoder().encode(updateUser.getPassword())); // Вы преобразуете пароль, только если он не пустой
        }
        user.setRoles(updateUser.getRoles());
        System.out.println("Updating user details: " + user);
        userRepository.save(user);
        System.out.println("User updated: " + user);
    }


    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }


    @Override
    @Transactional
    public User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User с данным id не найден"));
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.passwordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public User findByUserName(String username) {
        return userRepository.findUserByUsername(username);
    }


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User с именем '%s' не найден", username));
        }
        return user;
    }

}
