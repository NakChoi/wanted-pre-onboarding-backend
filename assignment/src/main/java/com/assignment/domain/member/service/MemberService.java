package com.assignment.domain.member.service;


import com.assignment.domain.member.entity.Member;
import com.assignment.domain.member.repository.MemberRepository;
import com.assignment.exception.CustomException;
import com.assignment.exception.ExceptionCode;
import com.assignment.security.utils.CustomAuthorityUtils;
import com.assignment.utils.CustomBeanUtils;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;
    private final CustomBeanUtils customBeanUtils;

    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());

        member.setPassword(encryptedPassword(member.getPassword()));

        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);

        Member savedMember = memberRepository.save(member);

        return savedMember;

    }


    @Transactional(readOnly = true)
    public Member findMember(Long memberId){

        return verifyExistsMemberId(memberId);
    }


    public void deleteMember(Long memberId) {

        memberRepository.deleteById(memberId);
    }

    private String encryptedPassword(String password){

        return passwordEncoder.encode(password);
    }


    private void verifyExistsEmail(String email){
        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isPresent()) {
            throw new CustomException(ExceptionCode.MEMBER_EXIST);
        }

    }

    private Member verifyExistsMemberId(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));

        return member;
    }

}





