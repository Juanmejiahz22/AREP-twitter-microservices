package com.twittersecure.feedservice.model;

public record PostItem(
    String id,
    String authorSub,
    String authorName,
    String content,
    String createdAt
) {
}
