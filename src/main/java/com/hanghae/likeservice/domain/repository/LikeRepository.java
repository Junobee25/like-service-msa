package com.hanghae.likeservice.domain.repository;

import com.hanghae.likeservice.domain.constant.LikeType;
import com.hanghae.likeservice.domain.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUserEmailAndLikeTypeAndTargetId(String userEmail, LikeType likeType, Long targetId);
}
