package com.twittersecure.feedservice.support;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;

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
}
