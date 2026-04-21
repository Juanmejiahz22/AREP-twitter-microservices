package com.twittersecure.userservice.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.twittersecure.userservice.model.UserContext;
import com.twittersecure.userservice.model.UserProfileItem;
import com.twittersecure.userservice.service.UserProfileService;
import com.twittersecure.userservice.support.EventFactory;
import com.twittersecure.userservice.util.AuthContextResolver;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class UserProfileHandlerTest {

    @Test
    void shouldRejectWhenUserContextIsMissing() {
        UserProfileService service = mock(UserProfileService.class);
        AuthContextResolver resolver = mock(AuthContextResolver.class);
        when(resolver.resolve(any())).thenReturn(Optional.empty());

        UserProfileHandler handler = new UserProfileHandler(service, resolver);
        APIGatewayV2HTTPResponse response = handler.handleRequest(EventFactory.meEvent(false), null);

        assertEquals(401, response.getStatusCode());
    }

    @Test
    void shouldReturnProfileWhenUserContextExists() {
        UserProfileService service = mock(UserProfileService.class);
        AuthContextResolver resolver = mock(AuthContextResolver.class);
        when(resolver.resolve(any())).thenReturn(Optional.of(new UserContext("auth0|123", "Test User", "test@example.com")));
        when(service.syncProfile(any())).thenReturn(
            new UserProfileItem("auth0|123", "Test User", "test@example.com", "2026-01-01T00:00:00Z")
        );

        UserProfileHandler handler = new UserProfileHandler(service, resolver);
        APIGatewayV2HTTPResponse response = handler.handleRequest(EventFactory.meEvent(true), null);

        assertEquals(200, response.getStatusCode());
    }
}
