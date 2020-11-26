package pl.politechnika.goalreacher.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.goalreacher.entity.AppUser;
import pl.politechnika.goalreacher.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/all") // DEV
    public ResponseEntity<Iterable<AppUser>> getAll()
    {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping()
    ResponseEntity<AppUser> getUser(Authentication authentication)
    {
        try
        {
            return new ResponseEntity<>(userService.getLoggedUser(authentication), HttpStatus.OK);
        } catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping
    ResponseEntity<AppUser> deleteUser(Authentication authentication)
    {
        try
        {
            userService.deleteUser(authentication);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping()
    ResponseEntity<AppUser> newUser(@RequestBody AppUser newUser)
    {
        AppUser check = userService.findByEmail(newUser.getEmail());

        if (check == null)
            return new ResponseEntity<>(userService.saveUser(newUser), HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @PutMapping()
    ResponseEntity<AppUser> updateUser(@RequestBody AppUser updatedUser, Authentication authentication)
    {
        try
        {
            return new ResponseEntity<>(userService.updateUser(updatedUser, authentication), HttpStatus.OK);
        } catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }
}
