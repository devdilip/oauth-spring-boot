package com.authentication.oauth.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {

    private String dbPassword;
    private String securityUserName;
    private String securityPassword;

}
