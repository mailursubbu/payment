package com.subra.payment.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

@Configuration
public class JdbcConfigs {
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @Qualifier("simpleAccountJdbcInsert")
    public SimpleJdbcInsert simpleAccountJdbcInsert(JdbcTemplate jdbcTemplate) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("ACCOUNT")
                .usingGeneratedKeyColumns("account_id");
        return simpleJdbcInsert;
    }

    @Bean
    @Qualifier("simpleTransactionJdbcInsert")
    public SimpleJdbcInsert simpleTransactionJdbcInsert(JdbcTemplate jdbcTemplate) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("TRANSACTION")
                .usingGeneratedKeyColumns("transaction_id");
        return simpleJdbcInsert;
    }


}
