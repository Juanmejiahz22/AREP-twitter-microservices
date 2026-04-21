package com.twittersecure.userservice.model;

public record UserProfileItem(
    String sub,
    String name,
    String email,
    String updatedAt
) {
}
