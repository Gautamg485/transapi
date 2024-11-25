package com.happy.transapi.config;

import com.happy.transapi.exceptions.AccessDeniedException;
import com.happy.transapi.requests.General.RequestMeta;
import com.happy.transapi.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Base64;


@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;
    private RequestMeta requestMeta;

    public JwtInterceptor(RequestMeta requestMeta){
        this.requestMeta = requestMeta;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  throws Exception {
        if(!(request.getRequestURI().contains("/favicon.ico")) && !(request.getRequestURI().contains("/api/v2/")) && !(request.getRequestURI().contains("/health")) && !(request.getRequestURI().contains("/error"))) {
            String auth = request.getHeader("Authorization");
            if (auth != null && !auth.isEmpty()) {
                String[] authSplit = request.getHeader("Authorization").split("Bearer");
                auth = authSplit.length>0 ? authSplit[1] : "";
            }
            JwtUtils jwtUtils1=new JwtUtils();

            Claims claims = jwtUtils1.verify(auth);

            requestMeta.setUserId(Long.valueOf(claims.getIssuer()));
        } else if (request.getRequestURI().contains("/api/v2/")) {
            String auth = request.getHeader("Authorization");
            if (auth != null && !auth.isEmpty()) {
                byte[] decodedBytes = Base64.getDecoder().decode(auth.substring(6));

                // Convert the decoded bytes to a string
                String decodedString = new String(decodedBytes);

                // Split the username and password
                String[] credentials = decodedString.split(":");
                String username = credentials[0];
                String password = credentials[1];

                if (!username.equals("transuser") || !password.equals("transuser@321go")) {
                    throw new AccessDeniedException("Access Denied");
                }
            }
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}