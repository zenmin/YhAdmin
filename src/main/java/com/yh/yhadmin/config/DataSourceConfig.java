package com.yh.yhadmin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/17 21:48
 */
@Configuration
@PropertySource("classpath:db.properties")
public class DataSourceConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

}
