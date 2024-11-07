package com.iteamoa.mypage.service;

import com.iteamoa.mypage.dto.FeedDto;
import com.iteamoa.mypage.repository.ApplicationRepository;
import com.iteamoa.mypage.repository.FeedRepository;
import com.iteamoa.mypage.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class WritingListService {
    private final ApplicationRepository applicationRepository;
    private final FeedRepository feedRepository;
    private final UserProfileRepository userProfileRepository;

}

