package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.List;

public interface RoleDao {

    Role getRoleByName(String roleName);

    void createRole(Role role);

    List<Role> getAllRoles();

    List<Role> getRolesByNames(List<String> roleNames);

}
