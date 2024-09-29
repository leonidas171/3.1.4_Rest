package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Role getRoleByName(String roleName) {
        return em.createQuery("select r from Role r where r.role = :roleName", Role.class)
                .setParameter("roleName", roleName)
                .getSingleResult();
    }

    @Override
    public void createRole(Role role) {
        em.persist(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return em.createQuery("select r from Role r", Role.class).getResultList();
    }

    @Override
    public List<Role> getRolesByNames(List<String> roleNames) {
        return em.createQuery("SELECT r FROM Role r WHERE r.role IN :roleNames", Role.class)
                .setParameter("roleNames", roleNames)
                .getResultList();
    }
}
