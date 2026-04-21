package com.twittersecure.userservice.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public final class MongoClientProvider {

    private static MongoClient client;

    private MongoClientProvider() {
    }

    public static MongoClient get(String uri) {
        if (client == null) {
            client = MongoClients.create(uri);
        }
        return client;
    }
}
