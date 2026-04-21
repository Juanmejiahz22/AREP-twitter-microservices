package com.twittersecure.feedservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonFactory {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonFactory() {
    }

    public static ObjectMapper mapper() {
        return OBJECT_MAPPER;
    }
}
