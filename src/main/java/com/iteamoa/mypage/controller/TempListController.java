package com.iteamoa.mypage.controller;

import com.iteamoa.mypage.dto.FeedDto;
import com.iteamoa.mypage.service.TempListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my/temp")
public class TempListController {

    private final TempListService tempListService;

    @GetMapping
    public ResponseEntity<?> getSavedFeedList(@RequestParam String creatorId, @RequestParam String feedType) {
        return ResponseEntity.ok(tempListService.getSavedList(creatorId, feedType));
    }

    @PatchMapping
    public ResponseEntity<?> updateFeed(@RequestBody FeedDto feedDto) {
        tempListService.updateFeed(feedDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
