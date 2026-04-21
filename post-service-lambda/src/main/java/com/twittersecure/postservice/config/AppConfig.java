package com.twittersecure.postservice.config;

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

    public boolean localAuthBypass() {
        return "true".equalsIgnoreCase(System.getenv("LOCAL_AUTH_BYPASS"));
    }

    private String read(String key) {
        String value = System.getenv(key);
        return value == null ? "" : value;
    }
}
