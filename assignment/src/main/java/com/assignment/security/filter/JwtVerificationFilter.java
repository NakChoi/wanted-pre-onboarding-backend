package com.assignment.security.filter;


import com.assignment.exception.CustomException;
import com.assignment.exception.ExceptionCode;
import com.assignment.security.jwt.JwtTokenizer;
import com.assignment.security.service.MemberDetailsService;
import com.assignment.security.utils.CustomAuthorityUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@AllArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils customAuthorityUtils;

    private final MemberDetailsService memberDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Map<String, Object> claims = verifyJws(request);
        setAuthenticationToContext(claims);

        filterChain.doFilter(request, response);
    }



    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");

        return authorization == null || !authorization.startsWith("Bearer");
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) {
        try{
            String jws = request.getHeader("Authorization").replace("Bearer ", "");

            Map<String, Object> claims = jwtTokenizer.getClaims(jws).getBody();

            return claims;
        }catch (ExpiredJwtException e){
            throw new CustomException(ExceptionCode.ACCESSTOKEN_EXPIRATION);
        }
    }


    private void setAuthenticationToContext(Map<String, Object> claims) {
        String username = (String) claims.get("username");
        UserDetails userDetails = memberDetailsService.loadUserByUsername(username);
        List<GrantedAuthority> authorities = customAuthorityUtils.createAuthorities((List) claims.get("roles"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
