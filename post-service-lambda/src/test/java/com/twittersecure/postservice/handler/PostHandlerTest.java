package com.twittersecure.postservice.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.twittersecure.postservice.model.PostItem;
import com.twittersecure.postservice.model.UserContext;
import com.twittersecure.postservice.service.PostService;
import com.twittersecure.postservice.support.EventFactory;
import com.twittersecure.postservice.util.AuthContextResolver;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class PostHandlerTest {

    @Test
    void shouldReturnPublicPosts() {
        PostService service = mock(PostService.class);
        AuthContextResolver resolver = mock(AuthContextResolver.class);
        when(service.listPosts()).thenReturn(List.of(
            new PostItem("1", "auth0|123", "Test User", "hello", "2026-01-01T00:00:00Z")
        ));

        PostHandler handler = new PostHandler(service, resolver);
        APIGatewayV2HTTPResponse response = handler.handleRequest(EventFactory.getEvent(), null);

        assertEquals(200, response.getStatusCode());
    }

    @Test
    void shouldRejectProtectedPostWithoutUserContext() {
        PostService service = mock(PostService.class);
        AuthContextResolver resolver = mock(AuthContextResolver.class);
        when(resolver.resolve(any())).thenReturn(Optional.empty());

        PostHandler handler = new PostHandler(service, resolver);
        APIGatewayV2HTTPResponse response = handler.handleRequest(
            EventFactory.postEvent("{\"content\":\"hello\"}", false),
            null
        );

        assertEquals(401, response.getStatusCode());
    }

    @Test
    void shouldCreateProtectedPostWithUserContext() {
        PostService service = mock(PostService.class);
        AuthContextResolver resolver = mock(AuthContextResolver.class);
        when(resolver.resolve(any())).thenReturn(Optional.of(new UserContext("auth0|123", "Test User", "test@example.com")));
        when(service.createPost(any(), any())).thenReturn(
            new PostItem("1", "auth0|123", "Test User", "hello", "2026-01-01T00:00:00Z")
        );

        PostHandler handler = new PostHandler(service, resolver);
        APIGatewayV2HTTPResponse response = handler.handleRequest(
            EventFactory.postEvent("{\"content\":\"hello\"}", true),
            null
        );

        assertEquals(200, response.getStatusCode());
    }
}
