package pl.politechnika.goalreacher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.politechnika.goalreacher.entity.User_;
import pl.politechnika.goalreacher.model.Credentials;
import pl.politechnika.goalreacher.service.UserService;
import pl.politechnika.goalreacher.utils.JWTUtils;

import java.util.Collections;

@Controller
public class LoginController {

    private final UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public LoginController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Credentials credentials) {
        User_ user = userService.findByEmail(credentials.getEmail());

        if (user == null || !bCryptPasswordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        String token = JWTUtils.getToken(user.getEmail());
        return new ResponseEntity<>(Collections.singletonMap("token", token), HttpStatus.OK);
    }

}
