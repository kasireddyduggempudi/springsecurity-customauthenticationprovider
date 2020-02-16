package com.vedantu.services;

import com.vedantu.daos.UserServiceDao;
import com.vedantu.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    private UserServiceDao userServiceDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
/*        System.out.println("in My user details service");
        List<GrantedAuthority> ga = new ArrayList<GrantedAuthority>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN"); // ROLE_ MUST BE THERE SINCE WE ARE SETTING AUTHORITY
        ga.add(authority);
        // here we can fetch the data from the database
        return new User("kasi","kasi", ga);*/

        UserModel userModel = userServiceDao.getUserDetailsByUsername(username);
        if(userModel == null){
            throw new AuthenticationCredentialsNotFoundException(username);
        }

        SimpleGrantedAuthority authority;
        ArrayList<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        for (String role : userModel.getAuthorities()) {
            authority = new SimpleGrantedAuthority(role);
            list.add(authority);
        }
        return new User(userModel.getUsername(), userModel.getPassword(), list);
    }
}