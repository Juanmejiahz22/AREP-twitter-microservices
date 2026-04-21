# Test report

## What is tested

### post-service
- handler returns public posts on `GET /posts`
- handler rejects protected `POST /posts` without user context
- handler accepts `POST /posts` with simulated authenticated context
- service rejects post content longer than 140 characters
- service creates and lists posts

### user-service
- handler rejects `/me` without user context
- handler returns profile for authenticated context
- service synchronizes user profile data

### feed-service
- handler returns feed successfully
- service returns feed data from repository

## Commands

Run each service independently:

```bash
cd post-service-lambda && mvn test
cd ../user-service-lambda && mvn test
cd ../feed-service-lambda && mvn test
```

## Local integration simulation

You can also run the gateway locally:

```bash
cd infrastructure
sam local start-api --env-vars env.json.example
```

Then call:
- `GET /feed`
- `GET /posts`
- `POST /posts` with local test headers
- `GET /me` with local test headers

## AWS validation that remains pending until deployment

The following must be validated after real deployment:

- real JWT validation by API Gateway
- real HTTP API URL
- deployed Lambda permissions and integration
- production frontend connectivity from S3 website hosting
