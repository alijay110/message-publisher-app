package com.sky.search.publisher.service;

import com.mongodb.util.JSON;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class PublishService {

    private MongoService mongoService;
    private JmsTemplate template;

    @Autowired
    public PublishService(MongoService mongoService, @Qualifier("topicTemplate") JmsTemplate template){
        this.mongoService = mongoService;
        this.template = template;
    }

    public void publishToTopic() {
        Iterator<Document> it = mongoService.getDataFromCollection().iterator();

        while (it.hasNext()){
            final String serializedString =  JSON.serialize(it.next());
            MessageCreator messageCreator = (session -> {
                System.out.println("Sending Message: " + serializedString);
                return session.createTextMessage(serializedString);
            });

            template.send(messageCreator);
        }
    }
}
