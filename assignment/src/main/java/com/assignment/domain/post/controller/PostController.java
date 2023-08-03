package com.assignment.domain.post.controller;


import com.assignment.domain.member.entity.Member;
import com.assignment.domain.post.dto.PostDto;
import com.assignment.domain.post.entity.Post;

import com.assignment.domain.post.mapper.PostMapper;
import com.assignment.domain.post.service.PostService;
import com.assignment.globalDto.MultiResponseDto;
import com.assignment.globalDto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/posts")
@Validated
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;


    @PostMapping
    public ResponseEntity postWriting(@AuthenticationPrincipal Member auth,
                                      @Valid @RequestBody PostDto.Post postDto) {

        postDto.setMemberId(auth.getMemberId());

        Post post = postMapper.postPostDtoToPost(postDto);
        Post savedPost = postService.writePost(post);

        return ResponseEntity.created(URI.create("/board/"+ savedPost.getPostId())).build();
    }


    @PatchMapping("/{post-id}")
    public ResponseEntity patchPost(@AuthenticationPrincipal Member auth,
                                    @PathVariable("post-id") @Positive Long postId, @Valid @RequestBody PostDto.Patch postPatchDto) {

        System.out.println("auth Member id"+ auth.getMemberId());
        postPatchDto.setMemberId(auth.getMemberId());


        Post post = postMapper.postPatchDtoToPost(postPatchDto);
        post.setPostId(postId);

        Post updatedPost = postService.updatePost(post);

        PostDto.Response response = postMapper.postToPostResponseDto(updatedPost);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/{post-id}")
    public ResponseEntity getPost(@PathVariable("post-id") @Positive Long postId) {
        Post post = postService.getPost(postId);

        PostDto.Response response = postMapper.postToPostResponseDto(post);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }


    //Question 페이지네이션
    @GetMapping
    public ResponseEntity getQuestions(@Positive @RequestParam int page, @Positive @RequestParam int size) {
        Page<Post> pagePosts = postService.findPosts(page - 1, size);
        List<Post> posts = pagePosts.getContent();

        List<PostDto.Response> responses = postMapper.postsToPostResponsesDto(posts);

        return new ResponseEntity<>(new MultiResponseDto<>(responses, pagePosts), HttpStatus.OK);
    }

    @DeleteMapping("/{post-id}")
    public ResponseEntity deletePost(@AuthenticationPrincipal Member member,
                                     @PathVariable("post-id") @Positive long postId) {

        postService.deletePost(postId, member);

        return ResponseEntity.noContent().build();
    }
}
