package com.sky.search.publisher.service;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sky.search.publisher.config.PropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MongoService {

    @Autowired
    private PropertiesConfig propertiesConfig;

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection collection;

    public MongoService() {}

    public FindIterable getDataFromCollection() {
        client = new MongoClient(propertiesConfig.getHost(), propertiesConfig.getPort());
        database = client.getDatabase(propertiesConfig.getDatabase());
        collection = database.getCollection(propertiesConfig.getCollection());

        return collection.find();
    }
}
