package com.howtographql.hackernews.repository;

import com.howtographql.hackernews.po.Link;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.*;

/**
 * Manages link persistence
 */
public class LinkRepository {

    private final MongoCollection<Document> links;

    public LinkRepository(MongoCollection<Document> links) {
        this.links = links;
    }

    public List<Link> getAllLinks() {
        List<Link> allLinks = new ArrayList<>();
        for (Document doc : links.find()) {
            allLinks.add(link(doc));
        }
        return allLinks;
    }

    public void saveLink(Link link) {
        Document doc = new Document();
        doc.append("url", link.getUrl());
        doc.append("description", link.getDescription());
        links.insertOne(doc);
    }

    private Link link(Document doc) {
        return new Link(
                doc.get("_id").toString(),
                doc.getString("url"),
                doc.getString("description"));
    }
}
