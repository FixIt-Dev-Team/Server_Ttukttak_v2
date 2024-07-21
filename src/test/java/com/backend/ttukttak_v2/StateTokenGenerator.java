package com.backend.ttukttak_v2;

import java.math.BigInteger;

public class StateTokenGenerator {
    
    public static void main(String[] args) {
        System.out.println(new StateTokenGenerator().generateState());
    }

    private String generateState() {
        String state = new BigInteger(130, new java.security.SecureRandom()).toString(32);

        return state;
    }
}
