package com.hsyou.wagu;

import com.hsyou.wagu.service.JwtTokenProvider;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

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

        String token = jwtTokenProvider.resolveToken(request);

        System.out.println("token"+token);
        if(token == null){
            filterChain.doFilter(request,response);
        }

        try{

            long id = jwtTokenProvider.validateTokenAndGetId(token);

            UsernamePasswordAuthenticationToken auth;
            auth = new UsernamePasswordAuthenticationToken(id,null,null);
            System.out.println("valid");
            SecurityContextHolder.getContext().setAuthentication(auth);
        }catch(Exception e){

            System.out.println("invlaid");
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request,response);
//        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
//        if (token != null) {
//            long id = jwtTokenProvider.validateTokenAndGetId(token);
//
//            request.setAttribute("id", id);
//        }else{
//            System.out.println("No Token");
//        }
//
//        System.out.println("token filter");
    }
}
