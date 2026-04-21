package com.twittersecure.feedservice.service;

import com.twittersecure.feedservice.model.PostItem;
import com.twittersecure.feedservice.repository.FeedRepository;
import java.util.List;

public class FeedService {

    private final FeedRepository repository;

    public FeedService(FeedRepository repository) {
        this.repository = repository;
    }

    public List<PostItem> listFeed() {
        return repository.findFeed();
    }
}
