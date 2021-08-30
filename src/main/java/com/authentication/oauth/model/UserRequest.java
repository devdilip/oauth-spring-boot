package com.authentication.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest {

    @NotNull (message = "FirstName should not be null")
    @NotBlank (message = "FirstName should not be empty")
    @Size(min = 5, max = 20,  message = "FirstName size must be between 5 and 30")
    private String firstName;

    @NotNull (message = "LastName should not be null")
    @NotBlank (message = "LastName should not be empty")
    @Size(min = 5, max = 20,  message = "LastName size must be between 5 and 30")
    private String lastName;

    @NotNull (message = "Email should not be null")
    @NotBlank (message = "Email should not be empty")
    @Size(min = 5, max = 30, message = "Email size must be between 5 and 30")
    private String email;

    @Size(min = 8, max = 13, message = "Mobile size must be between 8 and 13")
    @NotNull (message = "Mobile should not be null")
    @NotBlank (message = "Mobile should not be empty")
    private String mobile;
}
