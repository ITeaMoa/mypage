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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WritingListService {
    private final ApplicationRepository applicationRepository;
    private final FeedRepository feedRepository;
    private final UserProfileRepository userProfileRepository;

    public List<FeedDto> getWritingList(String creatorId, String sk) {
        List<FeedEntity> FeedEntities = feedRepository.findFeedByCreatorIdAndSk(creatorId, sk);
        List<FeedDto> feedDtos = new ArrayList<>();
        for (FeedEntity feedEntity : FeedEntities) {
            feedDtos.add(FeedDto.toFeedDto(feedEntity));
        }

        return feedDtos;
    }

    public List<ApplicationDto> getApplicationList(String feedId, String part) {
        List<ApplicationEntity> applicationEntities = applicationRepository.findApplication(feedId, part);
        List<ApplicationDto> applicationDtos = new ArrayList<>();
        for (ApplicationEntity applicationEntity : applicationEntities) {
            applicationDtos.add(ApplicationDto.toApplicationDto(applicationEntity, userProfileRepository.findByUserId(KeyConverter.toSeperatedId(applicationEntity.getPk()))));
        }
        return applicationDtos;
    }

    public void acceptApplication(ApplicationDto applicationDto) throws Exception {
        ApplicationEntity applicationEntity = applicationRepository.getApplication(applicationDto.getPk(), applicationDto.getSk());
        if (applicationEntity == null) {
            throw new Exception("Application does not exist");
        }
        applicationEntity.setStatus(StatusType.ACCEPTED);
        applicationRepository.updateApplication(applicationEntity);
    }

    public void rejectApplication(ApplicationDto applicationDto) throws Exception {
        ApplicationEntity applicationEntity = applicationRepository.getApplication(applicationDto.getPk(), applicationDto.getSk());
        if (applicationEntity == null) {
            throw new Exception("Application does not exist");
        }
        applicationEntity.setStatus(StatusType.REJECTED);
        applicationRepository.updateApplication(applicationEntity);
    }

    public void cancelApplication(ApplicationDto applicationDto) throws Exception {
        ApplicationEntity applicationEntity = applicationRepository.getApplication(applicationDto.getPk(), applicationDto.getSk());
        if (applicationEntity == null) {
            throw new Exception("Application does not exist");
        }
        applicationEntity.setStatus(StatusType.PENDING);
        applicationRepository.updateApplication(applicationEntity);
    }

    public void closeFeed(FeedDto feedDto) throws Exception {
        FeedEntity feedEntity = feedRepository.getFeed(feedDto.getPk(), feedDto.getSk());
        if (feedEntity == null) {
            throw new Exception("Feed does not exist");
        }
        feedEntity.setPostStatus(false);
        feedRepository.updateFeed(feedEntity);
        List<ApplicationEntity> applicationEntities = applicationRepository.findApplication(feedDto.getPk(), null);
        for (ApplicationEntity applicationEntity : applicationEntities) {
            if(applicationEntity.getStatus() == StatusType.PENDING) {
                applicationEntity.setStatus(StatusType.REJECTED);
                applicationRepository.updateApplication(applicationEntity);
            }
        }
    }

    public UserProfileDto getUserProfile(String userId) {
        return UserProfileDto.toUserProfileDto(userProfileRepository.findByUserId(userId));
    }

}

