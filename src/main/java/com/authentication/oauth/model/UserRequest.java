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

import static com.authentication.oauth.common.constants.AppConstants.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest {

    @NotBlank ( groups = { UserRequestGroup.class}, message = "{response.message.requiredFields}")
    @NotEmpty ( groups = { UserRequestGroup.class}, message = "{response.message.requiredFields}")
    @Pattern(groups = {UserRequestGroup.class}, regexp = NAME_REGEX, message = "{response.message.invalidProperties}")
    private String firstName;

    @NotBlank ( groups = { UserRequestGroup.class}, message = "{response.message.requiredFields}")
    @NotEmpty ( groups = { UserRequestGroup.class}, message = "{response.message.requiredFields}")
    @Pattern(groups = {UserRequestGroup.class}, regexp = NAME_REGEX, message = "{response.message.invalidProperties}")
    private String lastName;

    @NotBlank ( groups = {EmailGroup.class, UserRequestGroup.class}, message = "{response.message.requiredFields}")
    @NotEmpty (groups = {EmailGroup.class, UserRequestGroup.class},message = "{response.message.requiredFields}")
    @Pattern(groups = {EmailGroup.class, UserRequestGroup.class}, regexp = EMAIL_REGEX, message = "{response.message.invalidProperties}")
    private String email;

    @NotBlank (groups = {MobileGroup.class, UserRequestGroup.class},message = "{response.message.requiredFields}")
    @NotEmpty (groups = {MobileGroup.class, UserRequestGroup.class},message = "{response.message.requiredFields}")
    @Pattern(groups = {MobileGroup.class, UserRequestGroup.class}, regexp = MOBILE_REGEX, message = "{response.message.invalidProperties}")
    private String mobile;
}
