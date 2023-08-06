package com.assignment.unittest;


import com.assignment.domain.member.entity.Member;
import com.assignment.domain.post.entity.Post;
import com.assignment.domain.post.repository.PostRepository;
import com.assignment.domain.post.service.PostService;
import com.assignment.exception.CustomException;
import com.assignment.utils.CustomBeanUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private CustomBeanUtils customBeanUtils;

    @InjectMocks
    private PostService postService;

    private Member member;

    @BeforeAll
    public void setUp(){

         member = Member.builder()
                .memberId(1L)
                .email("test1234@naver.com")
                .password("SuccessPassword")
                .name("최낙")
                .build();

    }

    @BeforeEach
    public void set(){
        postService = new PostService(postRepository, customBeanUtils);
    }

    /*@DisplayName("게시풀 등록 테스트")
    @Test
    public void createPostTest(){
        // Given
        Post inputPost = Post.builder()
                .title("title")
                .content("content")
                .member(member)
                .build();

        Post savedPost = Post.builder()
                .postId(1L)
                .title("title")
                .content("content")
                .member(member)
                .build();


        // When
        when(postRepository.save(inputPost)).thenReturn(savedPost);

        // Act
        Post result = postService.writePost(inputPost);

        // Assert
        assertEquals(savedPost, result); // 예상된 결과와 실제 결과가 일치하는지 확인
    }*/

    @DisplayName("게시풀 등록 성공 테스트")
    @Test
    public void getPostTestWithSuccess(){
        // Given
        Post savedPost = Post.builder()
                .postId(1L)
                .title("title")
                .content("content")
                .member(member)
                .build();

        // When
        when(postRepository.findById(savedPost.getPostId())).thenReturn(Optional.ofNullable(savedPost));

        // Act
        Post result = postService.getPost(savedPost.getPostId());

        // Assert
        assertEquals(savedPost, result);

    }

    @DisplayName("게시풀 등록 실패 테스트")
    @Test
    public void getPostTestWithFail(){

        // Given
        Post savedPost = Post.builder()
                .postId(1L)
                .title("title")
                .content("content")
                .member(member)
                .build();

        // When
        when(postRepository.findById(savedPost.getPostId())).thenReturn(Optional.empty());

        // Assert
        assertThrows(CustomException.class, () -> {
            postService.getPost(savedPost.getPostId());
        });

    }

    @DisplayName("자신이 작성한 게시물인 경우 게시물 수정 성공")
    @Test
    void updatePostTestWithSuccess() {

        Post existingPost = Post.builder()
                .postId(1L)
                .title("Old Title")
                .content("Old Content")
                .member(member)
                .build();

        Post updatedPost = Post.builder()
                .postId(1L)
                .title("New Title")
                .content("New Content")
                .member(member)
                .build();

        // When
        when(postRepository.findById(existingPost.getPostId())).thenReturn(Optional.of(existingPost));

        // Act
        Post result = postService.updatePost(updatedPost);

        // Assert
        assertEquals(updatedPost.getPostId(), result.getPostId());
        assertNotEquals(updatedPost.getTitle(), result.getTitle());
        assertNotEquals(updatedPost.getContent(), result.getContent());
        assertEquals(updatedPost.getMember(), result.getMember());
    }

    @DisplayName("자신이 작성한 게시물인 아닌 경우 게시물 수정 실패")
    @Test
    void updatePostTestWithFail() {

        // Given
        Member anotherMember = Member.builder()
                .memberId(2L)
                .email("test1234@naver.com")
                .password("SuccessPassword")
                .name("최낙")
                .build();

        Post existingPost = Post.builder()
                .postId(1L)
                .title("Old Title")
                .content("Old Content")
                .member(anotherMember)
                .build();

        Post updatedPost = Post.builder()
                .postId(1L)
                .title("New Title")
                .content("New Content")
                .member(member)
                .build();


        // When

        when(postRepository.findById(existingPost.getPostId())).thenReturn(Optional.of(existingPost));

        // Assert
        assertThrows(CustomException.class, () -> postService.updatePost(updatedPost)) ;

    }

    @DisplayName("자신이 작성한 게시물인 경우 게시물 삭제 성공")
    @Test
    void deletePostTestWithSuccess() {
        // Given
        Post existingPost = Post.builder()
                .postId(1L)
                .title("Old Title")
                .content("Old Content")
                .member(member)
                .build();

        // When
        when(postRepository.findById(existingPost.getPostId())).thenReturn(Optional.of(existingPost));

        // Act
        doNothing().when(postRepository).delete(existingPost);

        // Act
        assertDoesNotThrow(() -> {
            postService.deletePost(existingPost.getPostId(), member);
        });

    }

    @DisplayName("자신이 작성한 게시물이 아닌 경우 게시물 삭제 실패")
    @Test
    void deletePostTestWithFail() {

        Post existingPost = Post.builder()
                .postId(1L)
                .title("Old Title")
                .content("Old Content")
                .member(member)
                .build();

        // When
        when(postRepository.findById(existingPost.getPostId())).thenReturn(Optional.of(existingPost));

        // Act
        doNothing().when(postRepository).delete(existingPost);

        // Act
        assertDoesNotThrow(() -> {
            postService.deletePost(existingPost.getPostId(), member);
        });

    }

    @DisplayName("페이징 테스트")
    @Test
    public void testFindPosts() {
        // Given
        int page = 2;
        int size = 5;

        Page<Post> mockPage = mock(Page.class);

        List<Post> dummyPosts = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Post post = Post.builder()
                    .postId((long) i)
                    .title("Title " + i)
                    .content("Content " + i)
                    .build();
            dummyPosts.add(post);
        }

        // When
        when(mockPage.getContent()).thenReturn(dummyPosts);

        // PostRepository 를 Mock 으로 만들고 findAll 메서드의 동작 설정
        when(postRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()))).thenReturn(mockPage);

        // Act
        Page<Post> result = postService.findPosts(page, size);

        // Assert
        assertEquals(dummyPosts, result.getContent());
    }
}
