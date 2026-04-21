package com.twittersecure.postservice.service;

import com.twittersecure.postservice.exception.ValidationException;
import com.twittersecure.postservice.model.CreatePostRequest;
import com.twittersecure.postservice.model.PostItem;
import com.twittersecure.postservice.model.UserContext;
import com.twittersecure.postservice.repository.PostRepository;
import java.util.List;

public class PostService {

    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<PostItem> listPosts() {
        return repository.findAllDesc();
    }

    public PostItem createPost(UserContext user, CreatePostRequest request) {
        String content = request.content() == null ? "" : request.content().trim();

        if (content.isBlank()) {
            throw new ValidationException("Post content is required.");
        }

        if (content.length() > 140) {
            throw new ValidationException("Post content must be 140 characters or less.");
        }

        return repository.create(user.sub(), user.name(), content);
    }
}
