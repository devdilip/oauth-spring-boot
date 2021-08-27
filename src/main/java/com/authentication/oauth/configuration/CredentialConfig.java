package com.authentication.oauth.configuration;

import com.authentication.oauth.common.domain.Credentials;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
@Slf4j
public class CredentialConfig {

    @Value("${file.location.secrets}")
    private String filePath;

    @Bean
    public Credentials credentials() throws IOException {
        log.debug("Configuring the credentials");
        File secretsFile = new File(filePath);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(secretsFile);
        String json = jsonNode.get("value").asText();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        log.debug("Configured the credentials");
        return mapper.readValue(json, Credentials.class);
    }
}
