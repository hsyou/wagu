package com.hsyou.wagu;

import com.hsyou.wagu.service.JwtTokenProvider;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;
    public JwtFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Override
    public void doFilter(
      ServletRequest request,
      ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);


        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        if (token != null) {
            long id = jwtTokenProvider.validateTokenAndGetId(token);

            request.setAttribute("id", id);
        }else{
            System.out.println("No Token");
        }

        System.out.println("token filter");
        chain.doFilter(request, response);
    }
}
