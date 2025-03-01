package com.iteamoa.mypage.controller;

import com.iteamoa.mypage.service.TempListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my/temp")
public class TempListController {

    private final TempListService tempListService;

    @GetMapping
    public ResponseEntity<?> getSavedFeedList(@RequestParam String creatorId, @RequestParam String feedType) {
        return ResponseEntity.ok(tempListService.getSavedList(creatorId, feedType));
    }
}
