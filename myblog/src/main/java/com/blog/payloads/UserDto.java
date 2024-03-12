package com.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;
    @NotEmpty
    @Size(min = 4, message = "Name Should be greater Than 4")
    private String name;
    @Email(message = "Enter Valid email")
    private String email;
    @NotEmpty
    @Size(min = 3,max = 10, message = "Password not empty")
    private String password;
    @NotEmpty
    private String about;
}
