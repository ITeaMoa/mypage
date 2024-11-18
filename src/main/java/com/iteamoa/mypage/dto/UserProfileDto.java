package com.iteamoa.mypage.dto;

import com.iteamoa.mypage.entity.UserProfileEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private String pk;
    private String sk;
    private String entityType;
    private String name;
    private String avatarUrl;
    private String headLine;
    private List<String> tags;
    private List<String> educations;
    private List<String> personalUrl;
    private List<String> experiences;
    private LocalDateTime timestamp;

    public static UserProfileDto toUserProfileDto(UserProfileEntity userProfileEntity) {
        return new UserProfileDto(
                userProfileEntity.getPk(),
                userProfileEntity.getSk(),
                userProfileEntity.getEntityType().getType(),
                userProfileEntity.getName(),
                userProfileEntity.getAvatarUrl(),
                userProfileEntity.getHeadLine(),
                userProfileEntity.getTags(),
                userProfileEntity.getEducations(),
                userProfileEntity.getPersonalUrl(),
                userProfileEntity.getExperiences(),
                userProfileEntity.getTimestamp()
        );
    }
}
