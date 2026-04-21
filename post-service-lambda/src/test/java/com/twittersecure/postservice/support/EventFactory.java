package com.twittersecure.postservice.support;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import java.util.HashMap;
import java.util.Map;

public final class EventFactory {

    private EventFactory() {
    }

    public static APIGatewayV2HTTPEvent getEvent() {
        APIGatewayV2HTTPEvent event = new APIGatewayV2HTTPEvent();
        APIGatewayV2HTTPEvent.RequestContext context = new APIGatewayV2HTTPEvent.RequestContext();
        APIGatewayV2HTTPEvent.RequestContext.Http http = new APIGatewayV2HTTPEvent.RequestContext.Http();
        http.setMethod("GET");
        context.setHttp(http);
        event.setRequestContext(context);
        return event;
    }

    public static APIGatewayV2HTTPEvent postEvent(String body, boolean authenticated) {
        APIGatewayV2HTTPEvent event = getEvent();
        event.getRequestContext().getHttp().setMethod("POST");
        event.setBody(body);
        if (authenticated) {
            Map<String, String> headers = new HashMap<>();
            headers.put("x-test-user-sub", "auth0|123");
            headers.put("x-test-user-name", "Test User");
            headers.put("x-test-user-email", "test@example.com");
            event.setHeaders(headers);
        }
        return event;
    }
}
