package com.howtographql.hackernews.resolver;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.howtographql.hackernews.po.Link;
import com.howtographql.hackernews.repository.LinkRepository;

public class Mutation implements GraphQLRootResolver {
    private final LinkRepository linkRepository;

    public Mutation(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public Link createLink(String url, String description ) {
        Link newLink = new Link(url, description);
        linkRepository.saveLink(newLink);
        return newLink;
    }

}
