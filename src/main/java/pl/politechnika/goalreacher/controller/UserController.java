package pl.politechnika.goalreacher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.politechnika.goalreacher.entity.AppUser;
import pl.politechnika.goalreacher.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController
{
    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/all") // DEV
    public ResponseEntity<Iterable<AppUser>> getAll()
    {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @PostMapping()
    ResponseEntity<AppUser> newUser(@RequestBody AppUser newUser)
    {
        AppUser check = userService.findByEmail(newUser.getEmail());
        if (check == null)
            return new ResponseEntity<>(userService.saveUser(newUser), HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
