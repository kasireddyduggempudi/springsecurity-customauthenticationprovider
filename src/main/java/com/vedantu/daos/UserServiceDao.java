package com.vedantu.daos;

import com.vedantu.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceDao {

    @Autowired
    private  MongoConnection mongoConnection;

    public MongoOperations getMongoOperations(){
        return mongoConnection.getMongoOperations();
    }

    public UserModel getUserDetailsByUsername(String username) {

        Query query = new Query(Criteria.where("username").is(username));
        return getMongoOperations().findOne(query, UserModel.class);
    }
}
