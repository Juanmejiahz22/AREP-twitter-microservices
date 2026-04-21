package com.twittersecure.userservice.handler;

import java.util.Optional;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.twittersecure.userservice.config.AppConfig;
import com.twittersecure.userservice.model.UserContext;
import com.twittersecure.userservice.repository.UserProfileRepository;
import com.twittersecure.userservice.service.UserProfileService;
import com.twittersecure.userservice.util.ApiResponseFactory;
import com.twittersecure.userservice.util.AuthContextResolver;

public class UserProfileHandler implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    private final UserProfileService service;
    private final AuthContextResolver authContextResolver;

    public UserProfileHandler() {
        AppConfig config = new AppConfig();
        this.service = new UserProfileService(new UserProfileRepository(config));
        this.authContextResolver = new AuthContextResolver(config);
    }

    public UserProfileHandler(UserProfileService service, AuthContextResolver authContextResolver) {
        this.service = service;
        this.authContextResolver = authContextResolver;
    }

    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent request, Context context) {

        String method = request.getRequestContext().getHttp().getMethod();

        if ("OPTIONS".equalsIgnoreCase(method)) {
            return ApiResponseFactory.noContent();
        }

        if (!"GET".equalsIgnoreCase(method)) {
            return ApiResponseFactory.methodNotAllowed();
        }

        Optional<UserContext> user = authContextResolver.resolve(request);

        return user.isPresent()
                ? ApiResponseFactory.ok(service.syncProfile(user.get()))
                : ApiResponseFactory.unauthorized("Missing authenticated user context.");
    }
}
