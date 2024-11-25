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

    private static long expiryDuration = 60 * 60 * 24;

    private static final String PUBLIC_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIY5MKq4r6Z/7tje3n4UK/iYmcEV89EHbV9BP5EDxnP8gyvq0ckNO3yZkBoCo7WkvOVmE9YK3DAw1WkYUQl/5+UCAwEAAQ==";

    // Static RSA public key
    private static final String PRIVATE_KEY = "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAhjkwqrivpn/u2N7efhQr+JiZwRXz0QdtX0E/kQPGc/yDK+rRyQ07fJmQGgKjtaS85WYT1grcMDDVaRhRCX/n5QIDAQABAkB57lQekp3GZCuGyZdW814qe/4Y4KdX+SZLRQvI4aQTUis9XK1g9PFZhv7TiFj+urTzOk2d1toOKlp/vB4AMHEhAiEA/7U+OzlLlzknJqirTJVb7RlqOSgKnohWkHNbHkar+kkCIQCGYG5AlJr82qrkCIGygUE7cW3fm1vEQQIJggK/W0cgvQIhAI483DQHZ/Pjl9KaSkccYfkehQbsLhQHVNefQ1UxDKL5AiAfu+Qtoiqb7jQPWCbw9e9mz1nIRdM9HLETd72YXUEF7QIfcaS5sOOQBj41Bs589HMN/NsYYa6GmTxsPHFHj8ZzTg==";

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
        long expiryTime = milliTime + expiryDuration * 1000;

        Date issuedAt = new Date(milliTime);
        Date expiryAt = new Date(expiryTime);

        // claims
        Claims claims = Jwts.claims()
                .setIssuer(user.getId().toString())
                .setIssuedAt(issuedAt)
                .setExpiration(expiryAt);

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