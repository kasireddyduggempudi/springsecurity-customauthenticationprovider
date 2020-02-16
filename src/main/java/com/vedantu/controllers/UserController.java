package com.vedantu.controllers;

import com.vedantu.requests.AuthenticationRequest;
import com.vedantu.responses.AuthenticationResponse;
import com.vedantu.services.MyUserDetailsService;
import com.vedantu.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/authenticate", method =  RequestMethod.POST)
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authRequest) throws Exception {
        try{
            System.out.println("authentication manager calling here");
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        }catch (Exception e){
            System.out.println("authentication failed");
            throw new Exception(e);
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @RequestMapping("/home")
    public String home(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        System.out.println(auth.getPrincipal());
        return "home page! Login required (:----";

    }

    @RequestMapping("/user")
    public String user(){
        return "Hello this is user protal";
    }

    @RequestMapping("/admin")
    public String admin(){
        return "Hello this is admin panel";
    }
}
