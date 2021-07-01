package com.restful.app.rest.controllers;

import com.restful.app.api.dto.UserDto;
import com.restful.app.api.dto.UserDtoEmailForgotPassword;
import com.restful.app.api.dto.UserLoginDto;
import com.restful.app.api.exceptions.IncorrectDataException;
import com.restful.app.api.services.IUserService;
import com.restful.app.entity.User;
import com.restful.app.rest.config.security.jwt.JwtProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping()
public class AuthenticationController {

    private final static String AUTH_HEADER = "Authorization";

    private final IUserService userService;
    private final JwtProvider jwtProvider;

    public AuthenticationController(IUserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> createUserAccount(@RequestBody @Valid UserDto user,
                                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IncorrectDataException("Field " + Objects.requireNonNull(bindingResult.getFieldError()).getField() + " " +
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            throw new IncorrectDataException("Passwords don't match");
        }
        if (this.userService.isUserExist(user.getEmail())) {
            throw new IncorrectDataException("Email already exists");
        }
        this.userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<HttpHeaders> authentication(@RequestBody @Valid UserLoginDto request) {
        HttpHeaders responseHeaders = new HttpHeaders();
        User user = userService.authentication(request.getEmail(), request.getPassword());
        String token = jwtProvider.generateToken(user.getEmail());
        responseHeaders.set(AUTH_HEADER, token);
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(null);
    }

    @PatchMapping("/login/forgot")
    public ResponseEntity<HttpStatus> forgotPassword(@RequestBody @Valid UserDtoEmailForgotPassword userDtoEmail , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IncorrectDataException("Field " + Objects.requireNonNull(bindingResult.getFieldError()).getField() + " " +
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        if (!this.userService.isUserExist(userDtoEmail.getEmail())) {
            throw  new IncorrectDataException("Email doesn't exist");
        }
        userService.changeForgotPassword(userDtoEmail.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
