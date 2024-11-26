package com.happy.transapi.utils;

import com.happy.transapi.entities.Users;
import com.happy.transapi.exceptions.AccessDeniedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {

//    private static long expiryDuration = 60 * 60 * 24;


    private static final String PUBLIC_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIYlSWpzdJ6Ts4FCLVc68oLnu9Yi++bGTCmMJQ/5JJ/WUWktHTcbFqWNv+v4dt7OEBn/cJ6SYDQprlb50hB4KMkCAwEAAQ==";

    // Static RSA public key
    private static final String PRIVATE_KEY = "MIIBUgIBADANBgkqhkiG9w0BAQEFAASCATwwggE4AgEAAkEAhiVJanN0npOzgUItVzrygue71iL75sZMKYwlD/kkn9ZRaS0dNxsWpY2/6/h23s4QGf9wnpJgNCmuVvnSEHgoyQIDAQABAj9jWaCfnOLCKQhiswbhk/oILMu9zJGQv7Lb31X6GCUH1KBSljvFs0qRL4N2WNox2ZE/gDmK/XSwIxCealTOFgECIQC1+fpoxOHXj2jOanK0CDOE6vm7/fXwXdVyMg9OET+HeQIhALy2eLSAWup44D5nPyAU2G88s8jSR2ARv4FVr3GbSEfRAiA9boImVDCZgIQ1CqJmPE6IFHryB6260zJ3NocMJ652oQIgA+41A2Vs/1c3LHWQDgYBIAf+op8ml2ynNXVoaxBV6BECIBOPc5x11pHDqdLQZqmmrhYFngflc4Prdioul2fVsNaS";

    // Get RSA private key
    private static PrivateKey getPrivateKey() throws Exception {
        // Remove header and footer from private key string
        String privateKeyString = PRIVATE_KEY
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("\n", "");
        // Convert private key string to byte array
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
        // Create PKCS8EncodedKeySpec from privateKeyBytes
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);
        // Create PrivateKey instance using KeyFactory
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    // Get RSA public key
    private static PublicKey getPublicKey() throws Exception {
        // Remove header and footer from public key string
        String publicKeyString = PUBLIC_KEY
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replace("\n", "");
        // Convert public key string to byte array
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
        // Create X509EncodedKeySpec from publicKeyBytes
        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
        // Create PublicKey instance using KeyFactory
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }
    public String generateJwt(Users user) throws Exception {
        long milliTime = System.currentTimeMillis();
//        long expiryTime = milliTime + expiryDuration * 1000;

        Date issuedAt = new Date(milliTime);
//        Date expiryAt = new Date(expiryTime);

        // claims
        Claims claims = Jwts.claims()
                .setIssuer(user.getId().toString())
                .setIssuedAt(issuedAt);

        // optional claims
        claims.put("userId", user.getId());
        claims.put("name", user.getName());
        claims.put("emailId", user.getEmail());

        // generate jwt using claims
        return "Bearer "+ Jwts.builder()
                            .setClaims(claims)
                            .signWith(SignatureAlgorithm.RS256, getPrivateKey())
                            .compact();
    }

    public Claims verify(String authorization) throws AccessDeniedException {
        try {
            Claims claims = Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(authorization).getBody();
            return claims;
        } catch(Exception e) {
            System.out.println("JWT Verify Exception - "+e.getMessage());
            throw new AccessDeniedException("Access Denied");
        }

    }
}