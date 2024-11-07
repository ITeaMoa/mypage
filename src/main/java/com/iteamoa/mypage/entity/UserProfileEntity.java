package com.iteamoa.mypage.entity;


import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.time.LocalDateTime;
import java.util.List;

@DynamoDbBean
public class UserProfileEntity {
    private String pk;
    private String sk;
    private String entityType;
    private String Name;
    private String avatarUrl;
    private String headLine;
    private List<String> tags;
    private List<String> educations;
    private List<String> personalUrl;
    private List<String> experiences;
    private LocalDateTime timestamp;
}
