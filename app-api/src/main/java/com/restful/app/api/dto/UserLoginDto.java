package com.restful.app.api.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UserLoginDto {

    @Size(min = 3,max = 50,message = "invalid login")
    private String email;
    @Size(min = 3,max = 120,message = "invalid password")
    private String password;
}
