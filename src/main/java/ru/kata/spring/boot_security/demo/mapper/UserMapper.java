package ru.kata.spring.boot_security.demo.mapper;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.entity.User;

@Component
public class UserMapper {

    public UserDTO toDto(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles()
        );
    }

    public User toEntity(UserDTO dto) {
        User user = new User(
                dto.getId(),
                dto.getName(),
                dto.getSurname(),
                dto.getEmail(),
                dto.getPassword()
        );
        dto.getRoles().forEach(user::addRoles);
        return user;
    }
}
