package com.msa.zuul_server.filter;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CustomPreFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(CustomPreFilter.class);
    private static final String REQUIRED_HEADER = "X-Auth-Key"; // Replace with your required header name
    private static final String REQUIRED_VALUE = "SecretValue"; // Replace with your required header value

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        // Log the request path
        String requestPath = exchange.getRequest().getPath().toString();
        logger.info("Pre-Filter: Request path -> {}", requestPath);

        // Check for the required header and value
        String headerValue = exchange.getRequest().getHeaders().getFirst(REQUIRED_HEADER);

        if (headerValue == null || !headerValue.equals(REQUIRED_VALUE)) {
            logger.warn("Unauthorized request: Missing or invalid header {}", REQUIRED_HEADER);

            // Set HTTP status to 401 Unauthorized
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete(); // End request processing
        }

        // Proceed with the chain if the header is valid
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1; // Priority of this filter (lower value means higher priority)
    }
}
