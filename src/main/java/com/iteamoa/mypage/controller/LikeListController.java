package com.iteamoa.mypage.controller;

import com.iteamoa.mypage.service.LikeListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my/like")
public class LikeListController {

    private final LikeListService likeListService;

    @GetMapping
    public ResponseEntity<?> getLikeFeedList(@RequestParam String userId, @RequestParam String feedType) {
        return ResponseEntity.ok(likeListService.getLikeFeed(userId, feedType));
    }

}
