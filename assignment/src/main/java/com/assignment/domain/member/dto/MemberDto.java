package com.assignment.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MemberDto {

    @Getter
    @Builder
    public static class Post{
        @NotBlank
        @Pattern(regexp = ".*@.*", message = "이메일 주소 형식이 아닙니다.")
        private String email;

        @NotBlank
        @Pattern(regexp = ".{8,}", message = "비밀번호는 8자 이상이어야 합니다.")
        private String password;

        @NotBlank
        private String name;

    }

    @Getter
    public static class Patch{
        @Pattern(regexp = ".{8,}", message = "비밀번호는 8자 이상이어야 합니다.")
        private String password;

    }

    @Getter
    @AllArgsConstructor
    public static class Response{

        private String memberId;

        private String email;

        private String name;

    }
}


