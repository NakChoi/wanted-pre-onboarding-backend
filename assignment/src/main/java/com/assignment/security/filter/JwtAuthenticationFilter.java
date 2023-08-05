package com.assignment.security.filter;

import com.assignment.domain.member.entity.Member;
import com.assignment.exception.CustomAuthenticationException;
import com.assignment.exception.ExceptionCode;
import com.assignment.security.dto.LoginDto;
import com.assignment.security.jwt.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenizer jwtTokenizer;


    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        ObjectMapper objectMapper = new ObjectMapper();
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);

        loginDtoValidTest(loginDto);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    public void loginDtoValidTest(LoginDto loginDto){
        isEmailValid(loginDto.getUsername());
        isPasswordValid(loginDto.getPassword());
    }


    private void isEmailValid(String email) {
        Pattern pattern = Pattern.compile(".*@.*");

        if(!pattern.matcher(email).matches()){
            throw new CustomAuthenticationException(ExceptionCode.EMAIL_FORMAT_INCORRECT);
        }
    }

    private void isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(".{8,}");

        if(!pattern.matcher(password).matches()){
            throw new CustomAuthenticationException(ExceptionCode.PASSWORD_FORMAT_INCORRECT);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {

        Member member = (Member) authResult.getPrincipal();

        String accessToken = jwtTokenizer.delegateAccessToken(member);

        response.setHeader("Authorization", "Bearer " + accessToken);

    }

}
