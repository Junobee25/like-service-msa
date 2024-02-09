package com.hanghae.likeservice.service;

import com.hanghae.likeservice.domain.constant.LikeType;
import com.hanghae.likeservice.domain.entity.Likes;
import com.hanghae.likeservice.domain.repository.LikeRepository;
import com.hanghae.likeservice.external.client.FeedServiceClient;
import com.hanghae.likeservice.external.client.UserServiceClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserServiceClient userServiceClient;
    private final FeedServiceClient feedServiceClient;

    @Transactional
    public void toggleLike(LikeType likeType, Long targetId, HttpHeaders headers) {
        Optional<Likes> optionalLike = likeRepository.findByUserEmailAndLikeTypeAndTargetId(userServiceClient.getMyEmail(headers), likeType, targetId);

        if (likeType == LikeType.ARTICLE) {
            if (Boolean.TRUE.equals(feedServiceClient.hasTarget(likeType, targetId))) {
                likeToggle(userServiceClient.getMyEmail(headers), likeType, optionalLike, targetId);
            }
        }

        if (likeType == LikeType.COMMENT) {
            if (Boolean.TRUE.equals(feedServiceClient.hasTarget(likeType, targetId))) {
                likeToggle(userServiceClient.getMyEmail(headers), likeType, optionalLike, targetId);
            }
        }
    }

    private void likeToggle(String currentUserEmail, LikeType likeType, Optional<Likes> optionalLike, Long targetId) {
        if (optionalLike.isPresent()) {
            Likes like = optionalLike.get();
            like.toggleDeleted();
            likeRepository.save(like);
        }

        if (optionalLike.isEmpty()) {
            Likes like = Likes.of(currentUserEmail, likeType, targetId);
            likeRepository.save(like);
        }
    }
}
