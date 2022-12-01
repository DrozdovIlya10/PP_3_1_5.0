package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImp implements RoleService{
    private final RoleDao roleDao;

    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> getListRoles() {
        return roleDao.getListRoles();
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Role> findByIdRoles(List<Long> roles) {
        return roleDao.findByIdRoles(roles);
    }

    @Transactional
    @Override
    public Role getIdForRole(long id){
        return roleDao.getIdForRole(id);
    }


}
