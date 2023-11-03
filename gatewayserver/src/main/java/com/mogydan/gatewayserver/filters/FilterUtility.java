package com.mogydan.gatewayserver.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import org.springframework.http.HttpHeaders;

@Component
public class FilterUtility {

    public static final String CORRELATION_ID = "mogydanbank-correlation-id";

    public String getCorrelationId(HttpHeaders requestHeaders) {
            return requestHeaders.getOrEmpty(CORRELATION_ID).stream().findFirst().orElse(null);
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange.mutate().request(exchange.getRequest().mutate().header(name, value).build()).build();
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
    }

}
