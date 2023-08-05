package com.assignment.domain.post.entity;


import com.assignment.domain.audit.Auditable;
import com.assignment.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Post extends Auditable {

    public Post(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
