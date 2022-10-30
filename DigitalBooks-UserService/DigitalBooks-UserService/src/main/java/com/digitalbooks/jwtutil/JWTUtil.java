package com.digitalbooks.jwtutil;

import java.util.Date;

import org.springframework.context.annotation.Configuration;

import com.digitalbooks.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Configuration
public class JWTUtil {
	
	private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000;
	private static final String SECRET_KEY = "DIGITALBOOKS-SECRET";
	
	public boolean validateAccessToken(String token) {
        try {
        	System.out.println("Validating JWT Token...");
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            System.out.println("JWT expired: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println("Token is null, empty or only whitespace: " + ex.getMessage());
        } catch (MalformedJwtException ex) {
            System.out.println("JWT is invalid: "+ ex);
        } catch (UnsupportedJwtException ex) {
            System.out.println("JWT is not supported: " + ex);
        } catch (SignatureException ex) {
            System.out.println("Signature validation failed");
        }
         
        return false;
    }
     
    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }
     
    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
	
	public String generateAccessToken(User user) {
		System.out.println("Generating access token...");
		return Jwts.builder()
				.setSubject(String.format("%s,%s", user.getUserId(), user.getUsername()))
				.setIssuer("DigitalBooks")
				.claim("roles", user.getUserRoles().toString())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();
	}

}
