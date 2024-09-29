package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService {

    List<User> index();

    User show(int id);

    void save(User user, List<String> role);

    void update(User user, List<String> role);

    void delete(int id);

    User getUserByName(String name);
}