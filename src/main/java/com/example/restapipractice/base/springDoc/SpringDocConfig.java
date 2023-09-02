package com.example.restapipractice.base.springDoc;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "REST API", version = "v1"))
public class SpringDocConfig {
}