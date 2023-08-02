package com.assignment.domain.member.controller;


import com.assignment.domain.member.dto.MemberDto;
import com.assignment.domain.member.entity.Member;
import com.assignment.domain.member.mapper.MemberMapper;
import com.assignment.domain.member.service.MemberService;
import com.assignment.globalDto.SingleResponseDto;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;


@Valid
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post memberPostDto){

        Member member = memberMapper.memberPostToMember(memberPostDto);

        Member createdMember = memberService.createMember(member);

        return ResponseEntity.created(URI.create("/member"+ createdMember.getMemberId())).build();
    }


    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive Long memberId) {
        Member member = memberService.findMember(memberId);
        MemberDto.Response response = memberMapper.memberToMemberResponse(member);

        return new ResponseEntity(new SingleResponseDto(response), HttpStatus.OK);
    }



    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive Long memberId) {
        memberService.deleteMember(memberId);

        return ResponseEntity.noContent().build();
    }
}



