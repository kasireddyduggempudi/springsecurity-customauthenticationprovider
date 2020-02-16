package com.vedantu.responses;

public class AuthenticationResponse {
    public String jwtToken;

    public AuthenticationResponse(){
        super();
    }

    public AuthenticationResponse(String jwtToken){
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
