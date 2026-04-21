package com.twittersecure.userservice.service;

import com.twittersecure.userservice.model.UserContext;
import com.twittersecure.userservice.model.UserProfileItem;
import com.twittersecure.userservice.repository.UserProfileRepository;

public class UserProfileService {

    private final UserProfileRepository repository;

    public UserProfileService(UserProfileRepository repository) {
        this.repository = repository;
    }

    public UserProfileItem syncProfile(UserContext context) {
        return repository.upsert(context.sub(), context.name(), context.email());
    }
}
