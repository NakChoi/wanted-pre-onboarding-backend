package com.assignment.unittest;

import com.assignment.domain.member.dto.MemberDto;
import com.assignment.domain.member.entity.Member;
import com.assignment.domain.member.repository.MemberRepository;
import com.assignment.domain.member.service.MemberService;
import com.assignment.security.utils.CustomAuthorityUtils;
import com.assignment.security.utils.CustomPasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {


    @Mock
    private CustomAuthorityUtils customAuthorityUtils;
    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        passwordEncoder = new CustomPasswordEncoder().passwordEncoder();
    }

    @DisplayName("Email Valid 테스트")
    @Test
    public void testValidEmail() {
        MemberDto.Post post = MemberDto.Post.builder()
                .email("test@gmail.com")
                .password("password")
                .name("최낙구")
                .build();

        // MemberDto.Post 객체에 대한 유효성 검증
        Set<ConstraintViolation<MemberDto.Post>> violations = validator.validate(post);

        // 유효성 검증 결과가 비어있는지 확인
        assertTrue(violations.isEmpty());
    }

    @DisplayName("Email Invalid 테스트")
    @Test
    public void testInvalidEmail() {
        MemberDto.Post post = MemberDto.Post.builder()
                .email("invalid-email")
                .password("password")
                .name("최낙구")
                .build();

        // MemberDto.Post 객체에 대한 유효성 검증
        Set<ConstraintViolation<MemberDto.Post>> violations = validator.validate(post);

        // 유효성 검증 결과 확인
        assertEquals(1, violations.size());
        assertEquals("이메일 주소 형식이 아닙니다.", violations.iterator().next().getMessage());
    }

    @DisplayName("Password Valid 테스트")
    @Test
    public void testValidPassword() {
        MemberDto.Post post = MemberDto.Post.builder()
                .email("test@gmail.com")
                .password("Valid-password")
                .name("최낙구")
                .build();

        // MemberDto.Post 객체에 대한 유효성 검증
        Set<ConstraintViolation<MemberDto.Post>> violations = validator.validate(post);

        // 유효성 검증 결과 확인
        assertTrue(violations.isEmpty());
    }


    @DisplayName("Password Invalid 테스트")
    @Test
    public void testInvalidPassword() {
        MemberDto.Post post = MemberDto.Post.builder()
                .email("test@gmail.com")
                .password("Invalid")
                .name("최낙구")
                .build();

        // MemberDto.Post 객체에 대한 유효성 검증
        Set<ConstraintViolation<MemberDto.Post>> violations = validator.validate(post);

        // 유효성 검증 결과 확인
        assertEquals(1, violations.size()); // 비밀번호는 8자 이상이어야 하는 경우 1개의 유효성 검증 오류가 발생해야 함
        assertEquals("비밀번호는 8자 이상이어야 합니다.", violations.iterator().next().getMessage());
    }



    @DisplayName("회원 가입 테스트")
    @Test
    public void signupTest(){
        // Given
        String originalPassword = "password";

        Member member = Member.builder()
                .email("test@gmail.com")
                .password("passwordEncoder.encode(originalPassword)")
                .build();


        // Mock 객체를 사용하여 메서드 호출에 대한 동작 설정
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        List<String> roles = Arrays.asList("ROLE_USER");
        when(customAuthorityUtils.createRoles(eq(member.getEmail()))).thenReturn(roles);

        // 테스트 대상 메서드 호출
        Member savedMember = memberService.createMember(member);

        // 테스트 검증
        assertNotNull(savedMember);
        assertNotEquals(originalPassword, savedMember.getPassword());
        assertEquals("test@gmail.com", savedMember.getEmail());
        assertEquals(roles, savedMember.getRoles());

    }

    @DisplayName("패스워드 암호화 테스트")
    @Test
    void passwordEncodeTest() {
        // given
        String rawPassWord = "test1234";

        // when
        String encodePassWord = passwordEncoder.encode(rawPassWord);

        // then
        assertNotEquals(rawPassWord, encodePassWord);
    }


    @DisplayName("패스워드 일치 테스트")
    @Test
    void matchPasswordTest() {
        // given
        String password = "test1234";
        String encodePassword = passwordEncoder.encode(password);
        String inputPassWord = "test1234";

        // when
        Boolean check = passwordEncoder.matches(inputPassWord, encodePassword);

        // then
        assertTrue(check);
    }

    @DisplayName("패스워드 불일치 테스트")
    @Test
    void notMatchTest() {
        // given
        String originalPassWord = "Password12";
        String inputPassWord = "12Password";
        String encodedPassword = passwordEncoder.encode(originalPassWord);

        // when
        Boolean check = passwordEncoder.matches(inputPassWord, encodedPassword);

        // then
        assertEquals(false, check);
    }

}
