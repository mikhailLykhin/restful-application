package com.restful.app.rest.config.security.handlers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.app.api.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
@Slf4j
public class CustomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    private static final String AUTHENTICATION_EXCEPTION = "You are not authorized";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        final ErrorDto errorDto = new ErrorDto(AUTHENTICATION_EXCEPTION);
        final OutputStream out = response.getOutputStream();
        final ObjectMapper mapper = new ObjectMapper();
        response.setStatus(401);
        mapper.writeValue(out, errorDto);
        out.flush();
    }
}
