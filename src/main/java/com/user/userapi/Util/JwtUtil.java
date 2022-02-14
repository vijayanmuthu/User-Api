package com.user.userapi.Util;

import java.nio.file.AccessDeniedException;
import java.sql.Date;

import com.user.userapi.Entity.Userdetail;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
	private static String secret = "look_up_mycode";
   private static long expiryDuration = 60 * 60;

    public String generateJwt(Userdetail userdetail) {
    	long milliTime = System.currentTimeMillis();
        long expiryTime = milliTime + expiryDuration * 1000;
        
        Date issuedAt = new Date(milliTime);
        Date expiryAt = new Date(expiryTime);
        
        Claims claims = Jwts.claims()
                .setIssuer(userdetail.getEmail().toString())
        .setIssuedAt(issuedAt)
        .setExpiration(expiryAt);

         return Jwts.builder()
                 .setClaims(claims)
                 .signWith(SignatureAlgorithm.HS512, secret)
                 .compact();
    }
    public Claims verify(String authorization) throws Exception {

        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization).getBody();
            return claims;
        } catch(Exception e) {
            throw new AccessDeniedException("Access Denied");
        }

    }
}
