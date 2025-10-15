package com.smessenger.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.jackson.ModelResolver;

// C.f. https://github.com/springdoc/springdoc-openapi/issues/66
@Configuration
public class OpenApiJacksonConfig {

  @Bean
  public ModelResolver swaggerModelResolver() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    ModelResolver resolver = new ModelResolver(mapper);
    ModelConverters.getInstance().addConverter(resolver);
    return resolver;
  }

}
