package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {
    List<Role> getListRoles();

    Set<Role> findByIdRoles(List<Long> roles);

    Role getIdForRole(long id);

    void setRoleForSave(Role role);
}
