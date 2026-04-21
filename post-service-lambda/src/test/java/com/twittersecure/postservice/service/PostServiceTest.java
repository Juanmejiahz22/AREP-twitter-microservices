package com.twittersecure.postservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.twittersecure.postservice.exception.ValidationException;
import com.twittersecure.postservice.model.CreatePostRequest;
import com.twittersecure.postservice.model.PostItem;
import com.twittersecure.postservice.model.UserContext;
import com.twittersecure.postservice.repository.PostRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class PostServiceTest {

    private final PostRepository repository = mock(PostRepository.class);
    private final PostService service = new PostService(repository);

    @Test
    void shouldCreatePost() {
        when(repository.create("auth0|123", "Test User", "hello")).thenReturn(
            new PostItem("1", "auth0|123", "Test User", "hello", "2026-01-01T00:00:00Z")
        );

        PostItem response = service.createPost(new UserContext("auth0|123", "Test User", "test@example.com"), new CreatePostRequest("hello"));

        assertEquals("hello", response.content());
    }

    @Test
    void shouldRejectTooLongContent() {
        assertThrows(ValidationException.class, () ->
            service.createPost(new UserContext("auth0|123", "Test User", null), new CreatePostRequest("a".repeat(141)))
        );
    }

    @Test
    void shouldListPosts() {
        when(repository.findAllDesc()).thenReturn(List.of(
            new PostItem("1", "auth0|123", "Test User", "hello", "2026-01-01T00:00:00Z")
        ));

        assertEquals(1, service.listPosts().size());
    }
}
