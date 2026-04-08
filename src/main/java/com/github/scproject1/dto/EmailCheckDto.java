package com.github.scproject1.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailCheckDto {
    private boolean isAvailable;
    private String message;
}
