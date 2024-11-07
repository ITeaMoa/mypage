package com.iteamoa.mypage.entity;

import com.iteamoa.mypage.constant.StatusType;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.time.LocalDateTime;

@DynamoDbBean
public class ApplicationEntity {
    private String pk;
    private String sk;
    private String entityType;
    private String part;
    private StatusType status;
    private String feedType;
    private LocalDateTime timestamp;
}
