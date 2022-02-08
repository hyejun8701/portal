package com.innocore.add.portal.controller;

import com.innocore.add.portal.vo.Role;
import com.innocore.add.portal.vo.User;
import com.innocore.add.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Random;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    /*@PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        if(!userService.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("not found with email=" + user.getEmail());
        }

        return userService.createUser(user);
    }*/

    @GetMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser() {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Random random = new Random();
        String generatedString = random.ints(97, 122 + 1)
                .limit(3)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        String temp = now + generatedString;

        User user = new User();
        user.setName(temp);
        user.setEmail(temp + "@mail.com");

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String password = bCryptPasswordEncoder.encode("innocore01!");

        user.setPassword(password);

        Role role = new Role();
        role.setId(1L);
        user.setRoles(Arrays.asList(role));

        return userService.createUser(user);
    }
}
