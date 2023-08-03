package com.assignment.domain.post.mapper;



import com.assignment.domain.post.dto.PostDto;
import com.assignment.domain.post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(source = "memberId", target = "member.memberId")
    Post postPostDtoToPost(PostDto.Post postPostDto);

    @Mapping(source = "memberId", target = "member.memberId")
    Post postPatchDtoToPost(PostDto.Patch postPatchDto);

    @Mapping(source = "member.memberId", target = "memberId")
    PostDto.Response postToPostResponseDto(Post question);

    List<PostDto.Response> postsToPostResponsesDto(List<Post> questions);
}
