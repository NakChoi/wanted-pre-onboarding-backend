package com.assignment.domain.member.mapper;



import com.assignment.domain.member.dto.MemberDto;
import com.assignment.domain.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member memberPostToMember(MemberDto.Post memberPostDto);

    Member memberPatchToMember(MemberDto.Patch memberPatchDto);

    MemberDto.Response memberToMemberResponse(Member member);

}



