package com.assignment.tdd;


import com.assignment.exception.CustomAuthenticationException;
import com.assignment.security.dto.LoginDto;
import com.assignment.security.filter.JwtAuthenticationFilter;
import com.assignment.security.jwt.JwtTokenizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("local")
public class MemberLoginValidTest {

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenizer jwtTokenizer;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    public void setUp() {
        jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer );
    }


    @Test
    @DisplayName("로그인 Email, Password Valid 검증 테스트")
    public void testAttemptAuthentication_ValidCredentials() {
        //Given
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("test@gmail.com");
        loginDto.setPassword("test1234");

        //When & Then
        assertDoesNotThrow(()-> jwtAuthenticationFilter.loginDtoValidTest(loginDto));

    }

    @Test
    @DisplayName("로그인 Email Invalid 검증 테스트")
    public void testAttemptAuthentication_InValidEmailCredentials() {
        //Given
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("invalid");
        loginDto.setPassword("test1234");

        // When
        CustomAuthenticationException exception = assertThrows(CustomAuthenticationException.class, ()-> jwtAuthenticationFilter.loginDtoValidTest(loginDto));

        // Then
        String message = exception.getMessage();
        assertEquals("Email format Incorrect", message);
    }

    @Test
    @DisplayName("로그인 Password Invalid 검증 테스트")
    public void testAttemptAuthentication_InValidPasswordCredentials() {
        //Given
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("test@gmail.com");
        loginDto.setPassword("invalid");

        // When
        CustomAuthenticationException exception = assertThrows(CustomAuthenticationException.class, ()-> jwtAuthenticationFilter.loginDtoValidTest(loginDto));

        // Then
        String message = exception.getMessage();
        assertEquals("Password format Incorrect", message);
    }

}
