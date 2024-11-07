package com.iteamoa.mypage.entity;

import com.iteamoa.mypage.utils.Comment;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@DynamoDbBean
public class FeedEntity {
    private String pk;
    private String sk;
    private String entityType;
    private String creatorId;
    private String title;
    private int recruitmentNum;
    private LocalDateTime deadline;
    private String place;
    private int period;
    private List<String> tags;
    private int likesCount;
    private String content;
    private List<Comment> comments;
    private boolean postStatus;
    private LocalDateTime timestamp;
    private boolean savedFeed;
    private Map<String, Integer> applyRoles;
    private Map<String, Integer> recruitmentRoles;
}
