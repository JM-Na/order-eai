package com.jmna.order_eai.config;

import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class BatchDataSourceConfig {

    @Bean
    @BatchDataSource
    public DataSource batchDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:batch-db")
                .username("sa")
                .password("")
                .driverClassName("org.h2.Driver")
                .build();
    }
}