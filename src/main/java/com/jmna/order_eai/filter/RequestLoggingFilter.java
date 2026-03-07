package com.jmna.order_eai.filter;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

/**
 * Request에 Id 부여 필터
 */
@Component
public class RequestLoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestId = UUID.randomUUID().toString();

        MDC.put("requestId", requestId);

        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove("requestId");
        }
    }
}
