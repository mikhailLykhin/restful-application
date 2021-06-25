package com.restful.app.rest.controllers;

import com.restful.app.api.dto.UserDto;
import com.restful.app.api.exceptions.IncorrectDataException;
import com.restful.app.api.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {

    private final IUserService userService;

    public AdminUserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public UserDto findUser(@PathVariable long id) {
        return this.userService.getUserById(id);
    }

    @GetMapping("/users")
    public List<UserDto> getUsers(Model model) {
        return this.userService.getAllUsers();
    }

    @PatchMapping("/users/rolechange/{id}")
    public ResponseEntity<HttpStatus> roleChangeUsers(@PathVariable("id") long id, @RequestBody UserDto user) {
        this.userService.roleChangeUser(id, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/users/activate/{id}")
    public ResponseEntity<HttpStatus> activateUsers(@PathVariable("id") long id, @RequestBody UserDto user) {
        this.userService.statusChangeUser(id, user.getStatus());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable int id) {
        if (!this.userService.isUserExist(id)) {
            throw new IncorrectDataException("User with id " + id + " doesn't exist");
        }
        this.userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users/s")
    public List<UserDto> getUsersBySearch(@RequestParam(value = "search") String search) {
        return this.userService.getUsersBySearchRequest(search);
    }
}
