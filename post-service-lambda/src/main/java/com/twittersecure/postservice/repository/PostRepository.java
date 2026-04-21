package com.twittersecure.postservice.repository;

import static com.mongodb.client.model.Sorts.descending;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.twittersecure.postservice.config.AppConfig;
import com.twittersecure.postservice.model.PostItem;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class PostRepository {

    private final AppConfig config;

    public PostRepository(AppConfig config) {
        this.config = config;
    }

    public List<PostItem> findAllDesc() {
        List<PostItem> posts = new ArrayList<>();
        collection().find().sort(descending("createdAt")).forEach(document -> posts.add(fromDocument(document)));
        return posts;
    }

    public PostItem create(String authorSub, String authorName, String content) {
        Document document = new Document()
            .append("authorSub", authorSub)
            .append("authorName", authorName)
            .append("content", content)
            .append("createdAt", Instant.now().toString());
        collection().insertOne(document);
        return fromDocument(document);
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
