package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.SchemaParser;
import com.howtographql.hackernews.repository.LinkRepository;
import com.howtographql.hackernews.resolver.Mutation;
import com.howtographql.hackernews.resolver.Query;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

import javax.servlet.annotation.WebServlet;

/**
 * The servlet acting as the GraphQL endpoint
 */
@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

    private static final LinkRepository linkRepository;

    static {
        MongoDatabase mongo = new MongoClient().getDatabase("graphql");
        linkRepository = new LinkRepository(mongo.getCollection("links"));
    }

    public GraphQLEndpoint() {
        super(buildSchema());
    }

    private static GraphQLSchema buildSchema() {
        GraphQLSchema result = SchemaParser.newParser().file("schema.graphqls").resolvers( //注册新的解析器
            new Query(linkRepository), new Mutation(linkRepository)).build().makeExecutableSchema();
        return result;
    }

}
