package com.authentication.oauth.model;

import com.authentication.oauth.model.validation.group.EmailGroup;
import com.authentication.oauth.model.validation.group.MobileGroup;
import com.authentication.oauth.model.validation.group.UserRequestGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest {

    @NotBlank ( groups = { UserRequestGroup.class}, message = "{response.message.requiredFields}")
    @NotEmpty ( groups = { UserRequestGroup.class}, message = "{response.message.requiredFields}")
    @Pattern(groups = {UserRequestGroup.class}, regexp = "^([a-z]+[,.]?[ ]?|[a-z]+['-]?)+$", message = "{response.message.invalidProperties}")
    private String firstName;

    @NotBlank ( groups = { UserRequestGroup.class}, message = "{response.message.requiredFields}")
    @NotEmpty ( groups = { UserRequestGroup.class}, message = "{response.message.requiredFields}")
    @Pattern(groups = {UserRequestGroup.class}, regexp = "^([a-z]+[,.]?[ ]?|[a-z]+['-]?)+$", message = "{response.message.invalidProperties}")
    private String lastName;

    @NotBlank ( groups = {EmailGroup.class, UserRequestGroup.class}, message = "{response.message.requiredFields}")
    @NotEmpty (groups = {EmailGroup.class, UserRequestGroup.class},message = "{response.message.requiredFields}")
    @Pattern(groups = {EmailGroup.class, UserRequestGroup.class}, regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", message = "{response.message.invalidProperties}")
    private String email;

    @NotBlank (groups = {MobileGroup.class, UserRequestGroup.class},message = "{response.message.requiredFields}")
    @NotEmpty (groups = {MobileGroup.class, UserRequestGroup.class},message = "{response.message.requiredFields}")
    @Pattern(groups = {MobileGroup.class, UserRequestGroup.class}, regexp = "^[0-9]+", message = "{response.message.invalidProperties}")
    private String mobile;
}
