package com.ideal.studentlog.helpers.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private int statusCode;
    private String status;


}