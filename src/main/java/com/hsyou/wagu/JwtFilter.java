package com.hsyou.wagu;

import com.hsyou.wagu.service.JwtTokenProvider;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;
    public JwtFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String requestURI = request.getRequestURI();
        if(requestURI.startsWith("/auth")){
            filterChain.doFilter(request,response);
        }
        System.out.println("Filter");

        String token = jwtTokenProvider.resolveToken(request);


        if(token == null){

            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            filterChain.doFilter(request,response);
        }

        try{

            long id = jwtTokenProvider.validateTokenAndGetId(token);

            UsernamePasswordAuthenticationToken auth;
            auth = new UsernamePasswordAuthenticationToken(id,null,null);

            System.out.println("Set Auth - "+id);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request,response);
        }catch(Exception e){


            SecurityContextHolder.clearContext();

            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        filterChain.doFilter(request,response);
        }
    }
}
