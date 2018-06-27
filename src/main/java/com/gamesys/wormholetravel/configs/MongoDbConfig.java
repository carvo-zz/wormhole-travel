package com.gamesys.wormholetravel.configs;

import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

//@Configuration
//@EnableMongoRepositories("com.gamesys.wormholetravel.repositories")
public class MongoDbConfig {

    @Value("${db.url}")
    private String dbUri;

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(new MongoClientURI(dbUri));
    }
}
