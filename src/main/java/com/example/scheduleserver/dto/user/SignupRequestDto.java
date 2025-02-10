package com.example.scheduleserver.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @Size(max = 10, message = "You can enter up to 10 characters.")
    @NotBlank(message = "'name' Must not be Empty.")
    private final String name;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "The email format is incorrect.")
    @NotBlank(message = "'email' Must not be Empty.")
    private final String email;

    @Size(max = 20, message = "You can enter up to 20 characters.")
    @NotBlank(message = "'password' Must not be Empty.")
    private final String password;


    public SignupRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
