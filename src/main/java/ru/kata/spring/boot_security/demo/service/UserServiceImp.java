package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImp implements UserService {
    private final UserDao userDao;
    private final RoleDao roleDao;

    public UserServiceImp(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Transactional
    @Override
    public void setIdForDelete(Long id) {
        userDao.setIdForDelete(id);
    }

    @Transactional
    @Override
    public void setUserForSave(User user) {
        userDao.setUserForSave(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getListUsers() {
        return userDao.getListUsers();
    }

    @Transactional
    @Override
    public User getIdForUser(long id) {
        return userDao.getIdForUser(id);
    }

    @Transactional
    @Override
    public void setUserForEdit(User user) {
        userDao.setUserForEdit(user);
    }

    @Transactional
    @Override
    public User getUserByEmail(String email) throws UsernameNotFoundException {
        return userDao.getUserByEmail(email);
    }

    @Transactional
    @Override
    public User getUserByUsername(String name) throws UsernameNotFoundException {
        return userDao.getUserByUsername(name);
    }

}
