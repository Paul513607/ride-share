package com.example.rideshare.rideshare.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class BeanConfig {
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}