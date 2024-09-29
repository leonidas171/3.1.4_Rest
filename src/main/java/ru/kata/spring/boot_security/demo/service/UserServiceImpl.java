package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

import static org.hibernate.internal.util.StringHelper.isBlank;


@Service
public class UserServiceImpl implements UserService {

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    private final UserDao userDao;

    public UserServiceImpl(RoleService roleService, PasswordEncoder passwordEncoder, UserDao userDao) {
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> index() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public User show(int id) {
        return userDao.getUser(id);
    }

    @Override
    @Transactional
    public void save(User user, List<String> roles) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().clear();
        user.getRoles().addAll(roleService.getRolesByNames(roles));
        userDao.saveUser(user);
    }

    @Override
    @Transactional
    public void update(User user, List<String> roles) {
        User updatable = userDao.getUser(user.getId());
        user.getRoles().clear();
        user.getRoles().addAll(roleService.getRolesByNames(roles));

        if (user.getRoles().isEmpty()) {
            User updatebleUser = userDao.getUser(user.getId());
            user.getRoles().addAll(updatebleUser.getRoles());
        }

        String password = isBlank(user.getPassword()) ? updatable.getPassword() : passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        userDao.updateUser(user);
    }

    @Override
    @Transactional
    public void delete(int id) {
        userDao.deleteUser(id);
    }

    @Override
    public User getUserByName(String name) {
        return userDao.getUserByUsername(name);
    }
}
