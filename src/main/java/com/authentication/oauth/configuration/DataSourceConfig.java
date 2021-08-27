package com.authentication.oauth.configuration;


import com.authentication.oauth.common.domain.Credentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    protected String dataSourceUrl;

    @Value("${spring.datasource.username}")
    protected String dbUserName;

    @Autowired
    private Credentials credentials;

    @Bean
    public DataSource getDataSource(){
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        log.debug("Configuring the dataSourceBuilder");
        dataSourceBuilder.url(dataSourceUrl);
        dataSourceBuilder.username(dbUserName);
        dataSourceBuilder.password(credentials.getDbPassword());
        log.debug("Configured the dataSourceBuilder");
        return dataSourceBuilder.build();
    }
}
