package com.iteamoa.mypage.service;

import com.iteamoa.mypage.constant.StatusType;
import com.iteamoa.mypage.dto.ApplicationDto;
import com.iteamoa.mypage.dto.FeedDto;
import com.iteamoa.mypage.dto.UserProfileDto;
import com.iteamoa.mypage.entity.ApplicationEntity;
import com.iteamoa.mypage.entity.FeedEntity;
import com.iteamoa.mypage.repository.ApplicationRepository;
import com.iteamoa.mypage.repository.FeedRepository;
import com.iteamoa.mypage.repository.UserProfileRepository;
import com.iteamoa.mypage.utils.KeyConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WritingListService {
    private final ApplicationRepository applicationRepository;
    private final FeedRepository feedRepository;
    private final UserProfileRepository userProfileRepository;

    public List<FeedDto> getWritingList(String creatorId, String sk) {
        return feedRepository.findFeedByCreatorIdAndSk(creatorId, sk).stream()
                .filter(FeedEntity::getUserStatus)
                .filter(feedEntity -> !feedEntity.getSavedFeed() && feedEntity.getPostStatus())
                .map(FeedDto::toFeedDto)
                .collect(Collectors.toList());
    }

    public List<ApplicationDto> getApplicationList(String feedId, String part) {
        return applicationRepository.findApplication(feedId, part).stream()
                .map(applicationEntity -> {
                    String userId = KeyConverter.toSeperatedId(applicationEntity.getPk());
                    return ApplicationDto.toApplicationDto(applicationEntity, userProfileRepository.findByUserId(userId));
                })
                .collect(Collectors.toList());
    }

    public void acceptApplication(ApplicationDto applicationDto) throws Exception {
        ApplicationEntity applicationEntity = Objects.requireNonNull(
                applicationRepository.getApplication(applicationDto.getPk(), applicationDto.getSk()),
                "Application does not exist"
        );
        applicationEntity.setStatus(StatusType.ACCEPTED);
        applicationRepository.updateApplication(applicationEntity);
    }

    public void rejectApplication(ApplicationDto applicationDto) throws Exception {
        ApplicationEntity applicationEntity = Objects.requireNonNull(
                applicationRepository.getApplication(applicationDto.getPk(), applicationDto.getSk()),
                "Application does not exist"
        );
        applicationEntity.setStatus(StatusType.REJECTED);
        applicationRepository.updateApplication(applicationEntity);
    }

    public void cancelApplication(ApplicationDto applicationDto) throws Exception {
        ApplicationEntity applicationEntity = Objects.requireNonNull(
                applicationRepository.getApplication(applicationDto.getPk(), applicationDto.getSk()),
                "Application does not exist"
        );
        applicationEntity.setStatus(StatusType.PENDING);
        applicationRepository.updateApplication(applicationEntity);
    }

    public void closeFeed(FeedDto feedDto) throws Exception {
        FeedEntity feedEntity = Objects.requireNonNull(
                feedRepository.getFeed(feedDto.getPk(), feedDto.getSk()),
                "Feed does not exist"
        );
        feedEntity.setPostStatus(false);
        feedRepository.updateFeed(feedEntity);
        applicationRepository.findApplication(feedDto.getPk(), null).stream()
            .filter(applicationEntity -> applicationEntity.getStatus() == StatusType.PENDING)
            .forEach(applicationEntity -> {
                applicationEntity.setStatus(StatusType.REJECTED);
                applicationRepository.updateApplication(applicationEntity);
            });
    }

    public UserProfileDto getUserProfile(String userId) {
        return UserProfileDto.toUserProfileDto(userProfileRepository.findByUserId(userId));
    }

}

