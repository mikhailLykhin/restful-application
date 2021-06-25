package com.restful.app.rest.config.security.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.app.api.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final String PERMISSION_EXCEPTION = "You don't have a permission";

    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        final ErrorDto errorDto = new ErrorDto(PERMISSION_EXCEPTION);
        final OutputStream out = response.getOutputStream();
        final ObjectMapper mapper = new ObjectMapper();
        response.setStatus(403);
        mapper.writeValue(out, errorDto);
        out.flush();
    }
}
