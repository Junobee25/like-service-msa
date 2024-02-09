package com.hanghae.likeservice.external.client;

import com.hanghae.likeservice.domain.constant.LikeType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="feed-service")
public interface FeedServiceClient {

    @GetMapping("/feed-service/{likeType}/{targetId}")
    Boolean hasTarget(@PathVariable LikeType likeType, @PathVariable Long targetId);
}
