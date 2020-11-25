package pl.politechnika.goalreacher.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pl.politechnika.goalreacher.entity.AppUser;
import pl.politechnika.goalreacher.model.Credentials;
import pl.politechnika.goalreacher.service.UserService;
import pl.politechnika.goalreacher.utils.JWTUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.UUID;

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
    public ResponseEntity<AppUser> login(HttpServletResponse res, @RequestBody Credentials credentials) {
        AppUser user = userService.findByEmail(credentials.getEmail());
        if (user == null || !bCryptPasswordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        res.addHeader("Authorization", JWTUtils.getToken(user.getEmail()));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/googleLogin")
    public ResponseEntity<AppUser> googleLogin(HttpServletResponse res, @RequestParam String googleAuthToken) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(System.getenv("CLIENT_ID")))
                .build();

        GoogleIdToken idToken = verifier.verify(googleAuthToken);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();

            AppUser user = userService.findByEmail(email);
            if (user == null) {
                user = new AppUser();
                user.setEmail(payload.getEmail());
                user.setFirstName((String) payload.get("given_name"));
                user.setLastName((String) payload.get("family_name"));
                user.setPassword(UUID.randomUUID().toString());
                user = userService.saveUser(user);
            }
            res.addHeader("Authorization", JWTUtils.getToken(user.getEmail()));
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
