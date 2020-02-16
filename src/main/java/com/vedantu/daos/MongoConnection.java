package com.vedantu.daos;

import com.mongodb.MongoClient;
import com.mongodb.ReadPreference;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

@Service
public class MongoConnection {

    MongoClient client;
    MongoTemplate template;
    MongoOperations mongoOperations;

    public MongoConnection() {
        super();
        initMongoOperations();
    }


    public MongoOperations initMongoOperations() {
        client = new MongoClient("localhost", 27017);

        template = new MongoTemplate(client, "springsecuritytesting");
        // here springsecuritytesting is db name
        template.setReadPreference(ReadPreference.primaryPreferred());

        mongoOperations = template;

        return mongoOperations;
    }





    public MongoOperations getMongoOperations() {
        return mongoOperations;
    }

    @PreDestroy
    public void cleanUp() {
        System.out.println("cleanup method is called");
        try {
            if(client != null) {
                client.close();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
