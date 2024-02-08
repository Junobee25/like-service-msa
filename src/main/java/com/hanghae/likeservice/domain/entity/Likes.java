package com.hanghae.likeservice.domain.entity;

import com.hanghae.likeservice.domain.constant.LikeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "user_email")
    private String userEmail;

    @Enumerated(EnumType.STRING)
    private LikeType likeType;

    private Long targetId;

    private Boolean deleted;

    public Likes() {

    }

    private Likes(String userEmail, LikeType likeType, Long targetId, Boolean deleted) {
        this.userEmail = userEmail;
        this.likeType = likeType;
        this.targetId = targetId;
        this.deleted = deleted;
    }

    public static Likes of(String userEmail, LikeType likeType, Long targetId) {
        return new Likes(userEmail, likeType, targetId, false);
    }
    public void toggleDeleted() {
        this.deleted = !this.deleted;
    }
}
