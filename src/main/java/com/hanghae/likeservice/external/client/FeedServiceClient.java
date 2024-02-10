package com.hanghae.likeservice.external.client;

import com.hanghae.likeservice.domain.constant.LikeType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name="feed-service")
public interface FeedServiceClient {

    @GetMapping("/feed-service/{likeType}/{targetId}")
    Boolean hasTarget(@PathVariable LikeType likeType, @PathVariable Long targetId);

    @GetMapping("/feed-service/posts/find-user-id")
    Optional<Long> getPostUserId(@RequestParam(value = "targetId", required = false) Long targetId);

    @GetMapping("/feed-service/comments/find-uesr-id")
    Optional<Long> getCommentUserId(@RequestParam(value = "targetId", required = false) Long targetId);
}
