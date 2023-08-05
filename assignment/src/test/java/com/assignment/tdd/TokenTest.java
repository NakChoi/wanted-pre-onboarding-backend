package com.assignment.tdd;

import com.assignment.domain.member.entity.Member;
import com.assignment.domain.member.service.MemberService;
import com.assignment.security.jwt.JwtTokenizer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TokenTest {

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private MemberService memberService;

    private String token;
    private Member member;

    @BeforeAll
    public void setUp() {
        Member savedMember = Member.builder()
                .email("test1234@naver.com")
                .password("SuccessPassword")
                .name("최낙")
                .build();

        member = memberService.createMember(savedMember);
        token = jwtTokenizer.delegateAccessToken(member);
    }

    @Test
    @DisplayName("토큰 발급 테스트")
    public void testGenerateToken() {
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    @DisplayName("토큰 파싱 테스트")
    public void testGetUsernameFromToken() {
        Map<String, Object> extractedUsername = jwtTokenizer.getClaims(token.replace("Bearer ", "")).getBody();

        assertEquals("test1234@naver.com", extractedUsername.get("username"));
    }

    @Test
    @DisplayName("토큰 유효성 통과 테스트")
    public void testValidateToken() {
        boolean isValidToken = jwtTokenizer.validToken(token);
        assertTrue(isValidToken);
    }

}
