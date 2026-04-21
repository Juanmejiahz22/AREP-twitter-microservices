package com.twittersecure.feedservice.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.twittersecure.feedservice.config.AppConfig;
import com.twittersecure.feedservice.repository.FeedRepository;
import com.twittersecure.feedservice.service.FeedService;
import com.twittersecure.feedservice.util.ApiResponseFactory;

public class FeedHandler implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    private final FeedService service;

    public FeedHandler() {
        this.service = new FeedService(new FeedRepository(new AppConfig()));
    }

    public FeedHandler(FeedService service) {
        this.service = service;
    }

    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent request, Context context) {

        String method = request.getRequestContext().getHttp().getMethod();

        if ("OPTIONS".equalsIgnoreCase(method)) {
            return ApiResponseFactory.noContent();
        }

        if ("GET".equalsIgnoreCase(method)) {
            return ApiResponseFactory.ok(service.listFeed());
        }

        return ApiResponseFactory.methodNotAllowed();
    }
}