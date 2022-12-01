package ru.kata.spring.boot_security.demo.dao;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    final BCryptPasswordEncoder bCryptPasswordEncoder;
    @PersistenceContext
    private EntityManager entityManager;

    public UserDaoImp(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void setUserForSave(User user) {
        user.setRoles(Collections.singleton(new Role(1L, "USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
        entityManager.flush();

    }

    @Override
    public void setIdForDelete(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getListUsers() {
        List<User> list = entityManager.createQuery("select user from User user ").getResultList();
        return list;
    }

    @Override
    public User getIdForUser(long id) {
        User user = entityManager.find(User.class, id);
        entityManager.detach(user);
        return user;
    }

    @Override
    public void setUserForEdit(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getUserByEmail(String email) {
        List<User> list = getListUsers();
        User user = list.stream().filter(user1 -> email.equals(user1.getEmail())).findAny().orElse(null);
        return user;
    }

    @Override
    public User getUserByUsername(String name) {
        List<User> list = getListUsers();
        User user = list.stream().filter(user1 -> name.equals(user1.getUsername())).findAny().orElse(null);
        return user;
    }


}

