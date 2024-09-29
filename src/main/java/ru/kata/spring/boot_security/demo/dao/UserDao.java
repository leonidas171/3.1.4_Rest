package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();

    User getUser(int id);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(int id);

    User getUserByUsername(String username);
}
