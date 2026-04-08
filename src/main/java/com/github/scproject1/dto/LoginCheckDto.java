package com.github.scproject1.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class LoginCheckDto {
    private String token;
    private String message;
}
