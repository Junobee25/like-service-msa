package com.hanghae.likeservice.controller;

import com.hanghae.likeservice.domain.constant.LikeType;
import com.hanghae.likeservice.dto.response.Response;
import com.hanghae.likeservice.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like-service")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{likeType}/{targetId}")
    private Response<Void> like(@PathVariable LikeType likeType, @PathVariable Long targetId, @RequestHeader HttpHeaders headers) {
        likeService.toggleLike(likeType, targetId, headers);
        return Response.success();
    }
}
