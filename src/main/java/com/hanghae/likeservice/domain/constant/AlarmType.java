package com.hanghae.likeservice.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmType {

    NEW_LIKE_ON_POST("new like"),
    NEW_LIKE_ON_COMMENT("new like");

    private final String alarmType;
}
