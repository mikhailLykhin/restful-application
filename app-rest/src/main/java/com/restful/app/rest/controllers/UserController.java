package com.restful.app.rest.controllers;

import com.restful.app.api.dto.UserDto;
import com.restful.app.api.dto.UserDtoMyAccount;
import com.restful.app.api.dto.UserDtoPasswordChange;
import com.restful.app.api.exceptions.IncorrectDataException;
import com.restful.app.api.services.IUserService;
import com.restful.app.entity.User;
import com.restful.app.utils.LogoFileUploader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") int id) {
        return this.userService.getUserById(id);
    }



    @GetMapping("/account")
    public UserDto editUser(Principal principal) {
        return this.userService.getUserByEmail(principal.getName());
    }

    @PostMapping("/upload")
    public ResponseEntity<HttpStatus> upload(Principal principal,
                                             @RequestParam("file") MultipartFile file) {
        this.userService.uploadLogo(principal, file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/account")
    public ResponseEntity<HttpStatus> updateUser(Principal principal,
                             @RequestBody @Valid UserDtoMyAccount user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
          throw new IncorrectDataException("Field " + Objects.requireNonNull(bindingResult.getFieldError()).getField() + " " +
                  Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        if (this.userService.isUserExist(user.getEmail()) && !principal.getName().equals(user.getEmail())) {
            throw new IncorrectDataException("Email already exists");

        }
        this.userService.updateUser(principal, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/changepass")
    public ResponseEntity<HttpStatus> changePassword(Principal principal, @RequestBody @Valid UserDtoPasswordChange userDtoPasswordChange) {
        if (!this.userService.isPasswordMatches(principal, userDtoPasswordChange)) {
           throw new IncorrectDataException("Current password is incorrect");
        }
        this.userService.updateUserPassword(principal, userDtoPasswordChange);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
