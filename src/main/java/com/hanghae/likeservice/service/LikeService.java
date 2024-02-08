package com.hanghae.likeservice.service;

import com.hanghae.likeservice.domain.constant.LikeType;
import com.hanghae.likeservice.domain.entity.Likes;
import com.hanghae.likeservice.domain.repository.LikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final RestTemplate restTemplate;
    private final LikeRepository likeRepository;

    @Transactional
    public void toggleLike(LikeType likeType, Long targetId, HttpHeaders headers) {
        Optional<Likes> optionalLike = likeRepository.findByUserEmailAndLikeTypeAndTargetId(getUserAccountEmail(headers).getBody(), likeType, targetId);

        if (likeType == LikeType.ARTICLE) {
            if (Boolean.TRUE.equals(hasTarget(targetId, likeType, headers).getBody())) {
                likeToggle(getUserAccountEmail(headers).getBody(), likeType, optionalLike, targetId);
            }
        }

        if (likeType == LikeType.COMMENT) {
            if (Boolean.TRUE.equals(hasTarget(targetId, likeType, headers).getBody())) {
                likeToggle(getUserAccountEmail(headers).getBody(), likeType, optionalLike, targetId);
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

    private ResponseEntity<String> getUserAccountEmail(HttpHeaders headers) {
        String userAccountUrl = "http://127.0.0.1:8000/user-service/users/email";
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(userAccountUrl, HttpMethod.GET, entity,
                String.class);
    }

    private ResponseEntity<Boolean> hasTarget(Long targetId, LikeType likeType, HttpHeaders headers) {
        String feedUrl = "http://127.0.0.1:8000/feed-service/{likeType}/{targetId}";

        URI url = UriComponentsBuilder.fromUriString(feedUrl)
                .buildAndExpand(likeType, targetId)
                .toUri();

        HttpEntity<Object> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, entity, Boolean.class);
    }
}
