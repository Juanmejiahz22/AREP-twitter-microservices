package com.twittersecure.feedservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.twittersecure.feedservice.model.PostItem;
import com.twittersecure.feedservice.repository.FeedRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class FeedServiceTest {

    @Test
    void shouldListFeed() {
        FeedRepository repository = mock(FeedRepository.class);
        when(repository.findFeed()).thenReturn(List.of(
            new PostItem("1", "auth0|123", "Test User", "hello", "2026-01-01T00:00:00Z")
        ));

        FeedService service = new FeedService(repository);

        assertEquals(1, service.listFeed().size());
    }
}
