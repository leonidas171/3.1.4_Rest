package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.mapper.UserMapper;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    private final UserMapper userMapper;

    public AdminController(UserService userService, UserMapper userMapper, RoleService roleService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.roleService = roleService;
    }

    @GetMapping({"/userinfo"})
    public UserDTO showUserInfo(Principal principal) {
        return userMapper.toDto(userService.getUserByName(principal.getName()));
    }

    @GetMapping("/roles")
    public List<String> getRoles() {
        return roleService.getAllRoles().stream().map(Role::getRole).collect(Collectors.toList());
    }

    @GetMapping("/users")
    public List<UserDTO> showAllUsers() {
        return userService.index().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public UserDTO showUserById(@PathVariable("id") int id) {
        return userMapper.toDto(userService.show(id));
    }

    @PostMapping("/users")
    public ResponseEntity<HttpStatus> createUser(@RequestBody UserDTO userDTO) {
        userService.save(userMapper.toEntity(userDTO), userDTO.getRoles().stream().map(Role::getRole).collect(Collectors.toList()));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody UserDTO userDTO, @PathVariable("id") long id) {
        userService.update(
                userMapper.toEntity(userDTO),
                userDTO.getRoles().stream().map(Role::getRole).collect(Collectors.toList())
        );
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}