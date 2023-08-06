package com.assignment.integrationtest;

import com.assignment.domain.member.entity.Member;
import com.assignment.domain.member.repository.MemberRepository;
import com.assignment.domain.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("local")
public class MemberLoginTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Member member;


    @BeforeAll
    public void generateMember() {
        Member savedMember = Member.builder()
                .email("test1234@naver.com")
                .password("SuccessPassword")
                .name("최낙")
                .build();

        member = memberService.createMember(savedMember);
    }


    @Test
    @DisplayName("사용자 로그인 성공 테스트")
    public void MemberLoginSuccessTest() throws Exception {
        Map<String, String> input = new HashMap<>();
        input.put("username", "test1234@naver.com");
        input.put("password", "SuccessPassword");

        ResultActions result = mockMvc.perform( MockMvcRequestBuilders
                        .get("/auth/login")
                        .content(objectMapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("사용자 로그인 성공 후 토큰 생성 테스트")
    public void MemberLoginGetAccessTokenTest() throws Exception {

        Map<String, String> input = new HashMap<>();
        input.put("username", "test1234@naver.com");
        input.put("password", "SuccessPassword");

        ResultActions result = mockMvc.perform( MockMvcRequestBuilders
                        .get("/auth/login")
                        .content(objectMapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(header().exists("Authorization"));

    }


    @Test
    @DisplayName("사용자 로그인 실패 테스트")
    public void MemberLoginFailTest() throws Exception {

        Map<String, String> input = new HashMap<>();
        input.put("username", "test1234@naver.com");
        input.put("password", "FailPassword");


        ResultActions result = mockMvc.perform( MockMvcRequestBuilders
                        .get("/auth/login")
                        .content(objectMapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}
