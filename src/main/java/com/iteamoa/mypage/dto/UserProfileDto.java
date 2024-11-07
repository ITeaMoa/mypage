package com.iteamoa.mypage.dto;

import java.time.LocalDateTime;
import java.util.List;

public class UserProfileDto {
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
