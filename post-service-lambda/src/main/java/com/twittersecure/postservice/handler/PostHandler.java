package com.twittersecure.postservice.handler;

import java.util.Optional;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.twittersecure.postservice.config.AppConfig;
import com.twittersecure.postservice.exception.ValidationException;
import com.twittersecure.postservice.model.CreatePostRequest;
import com.twittersecure.postservice.model.UserContext;
import com.twittersecure.postservice.repository.PostRepository;
import com.twittersecure.postservice.service.PostService;
import com.twittersecure.postservice.util.ApiResponseFactory;
import com.twittersecure.postservice.util.AuthContextResolver;
import com.twittersecure.postservice.util.JsonFactory;

public class PostHandler implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    private final PostService service;
    private final AuthContextResolver authContextResolver;

    public PostHandler() {
        AppConfig config = new AppConfig();
        this.service = new PostService(new PostRepository(config));
        this.authContextResolver = new AuthContextResolver(config);
    }

    public PostHandler(PostService service, AuthContextResolver authContextResolver) {
        this.service = service;
        this.authContextResolver = authContextResolver;
    }

    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent request, Context context) {
        try {
            String method = request.getRequestContext().getHttp().getMethod();

            if ("OPTIONS".equalsIgnoreCase(method)) {
                return ApiResponseFactory.noContent();
            }

            if ("GET".equalsIgnoreCase(method)) {
                return listPosts();
            }

            if ("POST".equalsIgnoreCase(method)) {
                return createPost(request);
            }

            return ApiResponseFactory.methodNotAllowed();

        } catch (ValidationException exception) {
            return ApiResponseFactory.badRequest(exception.getMessage());
        } catch (Exception exception) {
            return ApiResponseFactory.badRequest(exception.getMessage());
        }
    }

    private APIGatewayV2HTTPResponse listPosts() {
        return ApiResponseFactory.ok(service.listPosts());
    }

    private APIGatewayV2HTTPResponse createPost(APIGatewayV2HTTPEvent request) throws Exception {
        Optional<UserContext> user = authContextResolver.resolve(request);
        if (user.isEmpty()) {
            return ApiResponseFactory.unauthorized("Missing authenticated user context.");
        }

        CreatePostRequest payload = JsonFactory.mapper().readValue(request.getBody(), CreatePostRequest.class);
        return ApiResponseFactory.ok(service.createPost(user.get(), payload));
    }
}
