package com.twittersecure.feedservice.repository;

import static com.mongodb.client.model.Sorts.descending;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.twittersecure.feedservice.config.AppConfig;
import com.twittersecure.feedservice.model.PostItem;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class FeedRepository {

    private final AppConfig config;

    public FeedRepository(AppConfig config) {
        this.config = config;
    }

    public List<PostItem> findFeed() {
        List<PostItem> posts = new ArrayList<>();
        collection().find().sort(descending("createdAt")).forEach(document -> posts.add(fromDocument(document)));
        return posts;
    }

    private MongoCollection<Document> collection() {
        MongoDatabase db = MongoClientProvider.get(config.mongoUri()).getDatabase(config.database());
        return db.getCollection(config.postsCollection());
    }

    private PostItem fromDocument(Document document) {
        String id = document.getObjectId("_id") == null ? document.getString("id") : document.getObjectId("_id").toHexString();
        return new PostItem(
            id,
            document.getString("authorSub"),
            document.getString("authorName"),
            document.getString("content"),
            document.getString("createdAt")
        );
    }
}
