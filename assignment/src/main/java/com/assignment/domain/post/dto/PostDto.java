package com.assignment.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class PostDto {

    @Getter
    @Setter
    public static class Post{
        private long memberId;

        private String title;

        private String content;
    }

    @Getter
    @Setter
    public static class Patch{
        private long memberId;

        private String title;

        private String content;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class Response{
        private long memberId;

        private String title;

        private String content;
    }
}
