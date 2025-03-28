package com.iteamoa.mypage.service;

import com.iteamoa.mypage.dto.FeedDto;
import com.iteamoa.mypage.entity.FeedEntity;
import com.iteamoa.mypage.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TempListService {

    private final FeedRepository feedRepository;

    public List<FeedDto> getSavedList(String creatorId, String feedType) {
        return feedRepository.findFeedByCreatorIdAndSk(creatorId, feedType).stream()
                .filter(FeedEntity::getUserStatus)
                .filter(feedEntity -> !feedEntity.getPostStatus() && feedEntity.getSavedFeed())
                .map(FeedDto::toFeedDto)
                .collect(Collectors.toList());
    }

}
