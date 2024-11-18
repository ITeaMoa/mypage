package com.iteamoa.mypage.service;

import com.iteamoa.mypage.dto.FeedDto;
import com.iteamoa.mypage.entity.FeedEntity;
import com.iteamoa.mypage.repository.ApplicationRepository;
import com.iteamoa.mypage.repository.FeedRepository;
import com.iteamoa.mypage.repository.UserProfileRepository;
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
        List<FeedEntity> FeedEntities = feedRepository.getFeedByCreatorIdAndSk(creatorId, sk);
        List<FeedDto> feedDtos = new ArrayList<>();
        for (FeedEntity feedEntity : FeedEntities) {
            feedDtos.add(FeedDto.toFeedDto(feedEntity));
        }

        return feedDtos;
    }
}

