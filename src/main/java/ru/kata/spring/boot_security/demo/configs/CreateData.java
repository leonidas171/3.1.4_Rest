package ru.kata.spring.boot_security.demo.configs;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class CreateData {

    private final RoleService roleService;
    private final UserService userService;

    public CreateData(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        try {
            roleService.getRoleByName("ADMIN");
        } catch (Exception e) {
            Role adminRole = new Role();
            adminRole.setRole("ADMIN");
            roleService.createRole(adminRole);
        }

        try {
            userService.getUserByName("admin");
        } catch (Exception e) {
            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setName("admin");
            admin.setSurname("admin");
            admin.setPassword("admin");
            admin.addRoles(roleService.getRoleByName("ADMIN"));
            userService.save(admin, List.of("ADMIN"));
        }

        try {
            roleService.getRoleByName("USER");
        } catch (Exception e) {
            Role userRole = new Role();
            userRole.setRole("USER");
            roleService.createRole(userRole);
        }

        try {
            userService.getUserByName("user");
        } catch (Exception e) {
            User user = new User();
            user.setEmail("user@gmail.com");
            user.setName("user");
            user.setSurname("user");
            user.setPassword("user");
            user.addRoles(roleService.getRoleByName("USER"));
            userService.save(user, List.of("USER"));
        }
    }
}
