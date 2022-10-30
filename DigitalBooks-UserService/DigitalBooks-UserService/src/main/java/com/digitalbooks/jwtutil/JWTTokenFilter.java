package com.digitalbooks.jwtutil;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.digitalbooks.model.Role;
import com.digitalbooks.model.User;

import io.jsonwebtoken.Claims;

@Configuration
public class JWTTokenFilter extends OncePerRequestFilter {
	
	@Autowired
    private JWTUtil jwtUtil;
 
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	System.out.println("Inside JwtTokenFilter's doFilterInternal()");
        if (!hasAuthorizationBearer(request)) {
        	//remove line 37 after testing
        	//response.sendError(1, "Authorization header needed!");
            filterChain.doFilter(request, response);
            return;
        }
 
        String token = getAccessToken(request);
 
        if (!jwtUtil.validateAccessToken(token)) {
        	System.out.println("Token Invalid!!!");
            filterChain.doFilter(request, response);
            return;
        }
 
        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);
    }
 
    private boolean hasAuthorizationBearer(HttpServletRequest request) {
    	System.out.println("Checking Authorization header...");
        String header = request.getHeader("Authorization");
        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
            return false;
        }
 
        return true;
    }
 
    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.split(" ")[1].trim();
        return token;
    }
 
    private void setAuthenticationContext(String token, HttpServletRequest request) {
    	System.out.println("Inside setAuthenticationContext()");
        UserDetails userDetails = getUserDetails(token);
        System.out.println("User : " + userDetails.getUsername() + ", " + userDetails.getPassword());
        UsernamePasswordAuthenticationToken
            authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
 
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));
        System.out.println("Setting Authentication...");
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
 
    private UserDetails getUserDetails(String token) {
    	System.out.println("Inside getUserDetails()");
        User userDetails = new User();
        Claims claims = jwtUtil.parseClaims(token);
        String subject = (String) claims.get(Claims.SUBJECT);
        String roles = (String) claims.get("roles");
        System.out.println("User: " + userDetails); 
        roles = roles.replace("[", "").replace("]", "");
        String[] roleNames = roles.split(",");
        System.out.println("Roles: " + roleNames); 
        for (String aRoleName : roleNames) {
        	userDetails.addUserRole(new Role(aRoleName));
        }
        // Bug here(what if username has comma inside it?) 
        String[] jwtSubject = subject.split(",");
     
        userDetails.setUserId(Long.valueOf(jwtSubject[0]));
        userDetails.setUsername(jwtSubject[1]);
 
        return userDetails;
    }

}

