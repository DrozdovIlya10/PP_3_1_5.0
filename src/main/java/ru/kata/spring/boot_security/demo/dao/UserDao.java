package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    void setUserForSave(User user);

    List<User> getListUsers();

    void setIdForDelete(Long id);

    User getIdForUser(long id);

    void setUserForEdit(User user);

    User getUserByEmail(String email);

    User getUserByUsername(String name);

}
