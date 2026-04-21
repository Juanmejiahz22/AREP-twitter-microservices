package com.twittersecure.feedservice.config;

public class AppConfig {

    public String mongoUri() {
        return read("MONGODB_URI");
    }

    public String database() {
        return read("MONGODB_DATABASE");
    }

    public String postsCollection() {
        return read("MONGODB_POSTS_COLLECTION");
    }

    private String read(String key) {
        String value = System.getenv(key);
        return value == null ? "" : value;
    }
}
