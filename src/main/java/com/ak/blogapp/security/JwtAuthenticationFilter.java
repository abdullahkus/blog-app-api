package com.ak.blogapp.security;

import com.ak.blogapp.exception.TokenValidationException;
import com.ak.blogapp.response.result.ErrorDetail;
import com.ak.blogapp.response.result.FailedBaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // get JWT token from http request
        String token = getTokenFromRequest(request);

        try {
            // validate token
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {

                // get username from token
                String username = jwtTokenProvider.extractUser(token);

                // load the user associated with token
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            filterChain.doFilter(request, response);

            //TODO: Exception handler for Filter will be investigated
        } catch (TokenValidationException ex) {

            String path = (request.getQueryString() != null)
                    ? request.getRequestURI() + "?" + request.getQueryString()
                    : request.getRequestURI();

            String message = "Token validation failed: " + ex.getMessage();
            ErrorDetail errorDetail = new ErrorDetail(new Date(), path, message, HttpStatus.UNAUTHORIZED.toString());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(new ObjectMapper().writeValueAsString(FailedBaseResponse.fail(Collections.singletonList(errorDetail))));
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

}