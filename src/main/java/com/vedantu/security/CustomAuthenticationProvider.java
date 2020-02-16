package com.vedantu.security;

import com.vedantu.daos.UserServiceDao;
import com.vedantu.models.UserModel;
import com.vedantu.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;


@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private UserServiceDao userServiceDao;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username  = authentication.getPrincipal().toString();
        System.out.println("in custom provider");
        String password = authentication.getCredentials().toString();
        UserDetails details = myUserDetailsService.loadUserByUsername(username);

        if(details.getPassword().equals(password)){
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, password,details.getAuthorities());
            return auth;
        }else {
            throw new AuthenticationCredentialsNotFoundException(username);
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
