package com.twittersecure.postservice.util;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;

public final class ApiResponseFactory {

    private ApiResponseFactory() {
    }

    public static APIGatewayV2HTTPResponse ok(Object body) {
        return build(200, body);
    }

    public static APIGatewayV2HTTPResponse badRequest(String message) {
        return build(400, Map.of("message", message));
    }

    public static APIGatewayV2HTTPResponse unauthorized(String message) {
        return build(401, Map.of("message", message));
    }

    public static APIGatewayV2HTTPResponse methodNotAllowed() {
        return build(405, Map.of("message", "Method not allowed."));
    }

    public static APIGatewayV2HTTPResponse noContent() {
        return APIGatewayV2HTTPResponse.builder()
                .withStatusCode(204)
                .withHeaders(corsHeaders())
                .build();
    }

    private static Map<String, String> corsHeaders() {
        return Map.of(
                "Content-Type", "application/json",
                "Access-Control-Allow-Origin", "http://localhost:5173", // 👈 mejor que "*"
                "Access-Control-Allow-Headers", "Authorization,Content-Type",
                "Access-Control-Allow-Methods", "GET,POST,OPTIONS"
        );
    }

    private static APIGatewayV2HTTPResponse build(int status, Object body) {
        try {
            return APIGatewayV2HTTPResponse.builder()
                    .withStatusCode(status)
                    .withHeaders(corsHeaders())
                    .withBody(JsonFactory.mapper().writeValueAsString(body))
                    .build();
        } catch (Exception exception) {
            return APIGatewayV2HTTPResponse.builder()
                    .withStatusCode(500)
                    .withHeaders(corsHeaders())
                    .withBody("{\"message\":\"Serialization error.\"}")
                    .build();
        }
    }
}
