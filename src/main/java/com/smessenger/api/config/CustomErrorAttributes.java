package com.smessenger.api.config;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smessenger.api.dto.ErrorResponse;

import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

  private final ObjectMapper mapper;

  public CustomErrorAttributes() {
    this.mapper = new ObjectMapper();
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    this.mapper.setPropertyNamingStrategy(com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE);
  }

  @Override
  public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
    Throwable error = getError(webRequest);
    String message = "Unexpected error";
    String code = "INTERNAL_ERROR";

    if (error != null) {
      String className = error.getClass().getName();
      if (className.contains("HttpMessageNotReadableException")) {
        message = "Malformed JSON request";
        code = "INVALID_JSON";
      } else if (className.contains("io.jsonwebtoken")) {
        message = "Invalid or expired token";
        code = "INVALID_TOKEN";
      } else if (className.contains("AccessDeniedException")) {
        message = "Access denied";
        code = "ACCESS_DENIED";
      } else {
        // Do not expose internal exception messages in production
        message = "Internal server error";
        code = "INTERNAL_ERROR";
      }
    }

    ErrorResponse resp = new ErrorResponse(code, message);
    @SuppressWarnings("unchecked")
    Map<String, Object> body = mapper.convertValue(resp, Map.class);
    return body;
  }
}
