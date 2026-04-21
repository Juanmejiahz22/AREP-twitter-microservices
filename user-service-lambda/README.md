# User Service Lambda

Handles the authenticated profile endpoint by reading Auth0 user context and persisting a lightweight profile snapshot.

## Main variables

- `MONGODB_URI`
- `MONGODB_DATABASE`
- `MONGODB_POSTS_COLLECTION`
- `MONGODB_USERS_COLLECTION`
- `LOCAL_AUTH_BYPASS`
