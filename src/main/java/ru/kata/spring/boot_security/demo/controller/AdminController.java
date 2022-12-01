package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping()
public class AdminController {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<?> list() {
        try {
            List<User> list = userService.getListUsers();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<?> user(Model model, @PathVariable("id") long id) {
        try {
            User user = userService.getIdForUser(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/user/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @Valid @RequestBody User user) {
        try {

            User user1 = userService.getIdForUser(id);
            user1.setUsername(user.getUsername());
            user1.setLastname(user.getLastname());
            user1.setAge(user.getAge());
            user1.setEmail(user.getEmail());
            user1.setRoles(user.getRoles());
            user1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userService.setUserForEdit(user1);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {

        try {
            userService.setIdForDelete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/users")
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            userService.setUserForSave(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(value = "/user")
    public ResponseEntity<?> show() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByUsername(auth.getName());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
