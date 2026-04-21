package com.twittersecure.userservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.twittersecure.userservice.model.UserContext;
import com.twittersecure.userservice.model.UserProfileItem;
import com.twittersecure.userservice.repository.UserProfileRepository;
import org.junit.jupiter.api.Test;

class UserProfileServiceTest {

    @Test
    void shouldSyncProfile() {
        UserProfileRepository repository = mock(UserProfileRepository.class);
        when(repository.upsert("auth0|123", "Test User", "test@example.com")).thenReturn(
            new UserProfileItem("auth0|123", "Test User", "test@example.com", "2026-01-01T00:00:00Z")
        );

        UserProfileService service = new UserProfileService(repository);

        UserProfileItem response = service.syncProfile(new UserContext("auth0|123", "Test User", "test@example.com"));

        assertEquals("test@example.com", response.email());
    }
}
