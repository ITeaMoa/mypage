package com.iteamoa.mypage.service;

import com.iteamoa.mypage.dto.FeedDto;
import com.iteamoa.mypage.entity.FeedEntity;
import com.iteamoa.mypage.repository.FeedRepository;
import com.iteamoa.mypage.repository.LikeRepository;
import com.iteamoa.mypage.utils.KeyConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeListService {

    private final LikeRepository likeRepository;
    private final FeedRepository feedRepository;

    public List<FeedDto> getLikeFeed(String userId, String feedType) {
        return likeRepository.queryLikeFeed(userId).stream()
                .map(likeEntity -> feedRepository.getFeed(KeyConverter.toSeperatedId(likeEntity.getSk()), feedType))
                .filter(FeedEntity::getPostStatus)
                .map(FeedDto::toFeedDto)
                .collect(Collectors.toList());
    }
}
