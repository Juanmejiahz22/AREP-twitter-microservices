package com.twittersecure.feedservice.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.twittersecure.feedservice.model.PostItem;
import com.twittersecure.feedservice.service.FeedService;
import com.twittersecure.feedservice.support.EventFactory;
import java.util.List;
import org.junit.jupiter.api.Test;

class FeedHandlerTest {

    @Test
    void shouldReturnFeed() {
        FeedService service = mock(FeedService.class);
        when(service.listFeed()).thenReturn(List.of(
            new PostItem("1", "auth0|123", "Test User", "hello", "2026-01-01T00:00:00Z")
        ));

        FeedHandler handler = new FeedHandler(service);
        APIGatewayV2HTTPResponse response = handler.handleRequest(EventFactory.getEvent(), null);

        assertEquals(200, response.getStatusCode());
    }
}
