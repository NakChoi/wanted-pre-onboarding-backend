package com.assignment.domain.post.service;

import com.assignment.domain.member.entity.Member;
import com.assignment.domain.post.entity.Post;
import com.assignment.domain.post.repository.PostRepository;
import com.assignment.exception.CustomException;
import com.assignment.exception.ExceptionCode;
import com.assignment.utils.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final CustomBeanUtils customBeanUtils;

    public Post writePost(Post post){

        System.out.println(post.getContent());
        System.out.println(post.getMember().getMemberId());
        System.out.println(post.getTitle());

        Post savedPost = postRepository.save(post);



        return savedPost;
    }

    public Post getPost(long questionId) {
        Post verifiedPost = verifyBoardById(questionId);

        return verifiedPost;
    }

    public Page<Post> findPosts(int page, int size) {

            Page<Post> questionPage = postRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));

            return questionPage;
    }

    public Post updatePost(Post post){
        Post verifiedPost = verifyBoardById(post.getPostId());

        if (!Objects.equals(post.getMember().getMemberId(), verifiedPost.getMember().getMemberId())) {
            throw new CustomException(ExceptionCode.MEMBER_NOT_MATCH);
        }

        customBeanUtils.copyNonNullProperties(post, verifiedPost);

        return verifiedPost;
    }

    public void deletePost(Long id, Member member) {

        Post verifiedPost = verifyBoardById(id);

        if (!Objects.equals(verifiedPost.getMember().getMemberId(), member.getMemberId())) {
            throw new CustomException(ExceptionCode.MEMBER_NOT_MATCH);
        }

        postRepository.delete(verifiedPost);
    }

    private Post verifyBoardById(Long id){

        Post verifiedPost = postRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionCode.POST_EXISTS));

        return verifiedPost;
    }

}
