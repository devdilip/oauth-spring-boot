package com.authentication.oauth.model;

import com.authentication.oauth.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetailResponse {
    private Integer id;
    private String name;
    private String email;
    private String mobile;

    public UserDetailResponse(User user){
        this.setName(user.getFirstName() + " " + user.getLastName());
        this.setEmail(user.getEmail());
        this.setMobile(user.getMobile());
    }
}
