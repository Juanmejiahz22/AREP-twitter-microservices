package com.twittersecure.userservice.util;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.twittersecure.userservice.config.AppConfig;
import com.twittersecure.userservice.model.UserContext;
import java.util.Map;
import java.util.Optional;

public class AuthContextResolver {

    private final AppConfig config;

    public AuthContextResolver(AppConfig config) {
        this.config = config;
    }

    public Optional<UserContext> resolve(APIGatewayV2HTTPEvent request) {
        Optional<UserContext> fromGateway = fromGatewayClaims(request);
        return fromGateway.isPresent() ? fromGateway : fromLocalHeaders(request);
    }

    private Optional<UserContext> fromGatewayClaims(APIGatewayV2HTTPEvent request) {
        try {
            Map<String, String> claims = request.getRequestContext().getAuthorizer().getJwt().getClaims();
            return Optional.of(new UserContext(claims.get("sub"), claims.get("name"), claims.get("email")));
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }

    private Optional<UserContext> fromLocalHeaders(APIGatewayV2HTTPEvent request) {
        if (!config.localAuthBypass() || request.getHeaders() == null) {
            return Optional.empty();
        }

        String sub = request.getHeaders().get("x-test-user-sub");
        if (sub == null || sub.isBlank()) {
            return Optional.empty();
        }

        return Optional.of(new UserContext(
            sub,
            request.getHeaders().getOrDefault("x-test-user-name", "Local Test User"),
            request.getHeaders().get("x-test-user-email")
        ));
    }
}
