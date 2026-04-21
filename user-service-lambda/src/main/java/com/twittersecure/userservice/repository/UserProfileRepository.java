package com.twittersecure.userservice.repository;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import com.twittersecure.userservice.config.AppConfig;
import com.twittersecure.userservice.model.UserProfileItem;
import java.time.Instant;
import org.bson.Document;

public class UserProfileRepository {

    private final AppConfig config;

    public UserProfileRepository(AppConfig config) {
        this.config = config;
    }

    public UserProfileItem upsert(String sub, String name, String email) {
        Document document = new Document()
            .append("sub", sub)
            .append("name", name)
            .append("email", email)
            .append("updatedAt", Instant.now().toString());

        collection().replaceOne(eq("sub", sub), document, new ReplaceOptions().upsert(true));
        return fromDocument(collection().find(eq("sub", sub)).first());
    }

    private MongoCollection<Document> collection() {
        MongoDatabase db = MongoClientProvider.get(config.mongoUri()).getDatabase(config.database());
        return db.getCollection(config.usersCollection());
    }

    private UserProfileItem fromDocument(Document document) {
        return new UserProfileItem(
            document.getString("sub"),
            document.getString("name"),
            document.getString("email"),
            document.getString("updatedAt")
        );
    }
}
