package pl.politechnika.goalreacher.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping()
    ResponseEntity<AppUser> getUser(Authentication authentication)
    {
        AppUser loggedUser = userService.findByEmail(authentication.getPrincipal().toString());
        if (loggedUser == null)
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(loggedUser, HttpStatus.OK);
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
        AppUser appUser = userService.updateUser(updatedUser, authentication);
        if (appUser == null)
        {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(appUser, HttpStatus.OK);
    }

    @PutMapping("/updatePlayerId")
    ResponseEntity<AppUser> updatePlayerId(@RequestParam String playerId, Authentication authentication)
    {
        AppUser user = userService.findByEmail(authentication.getPrincipal().toString());

        if (user == null)
        {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>(userService.updatePlayerId(playerId, user), HttpStatus.OK);
    }
}
