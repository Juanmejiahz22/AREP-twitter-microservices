# Frontend React

This frontend is shared across both phases of the project.

- In the monolith package, set `VITE_API_MODE=monolith`.
- In the microservices package, set `VITE_API_MODE=api-gateway`.
- Authentication is handled with the Auth0 React SDK.
- The production website URL documented for this project is `http://ms-front-prod.s3-website-us-east-1.amazonaws.com`.

## Main environment variables

- `VITE_AUTH0_DOMAIN`
- `VITE_AUTH0_CLIENT_ID`
- `VITE_AUTH0_AUDIENCE`
- `VITE_API_MODE`
- `VITE_API_BASE_URL`
- `VITE_API_GATEWAY_BASE_URL`
