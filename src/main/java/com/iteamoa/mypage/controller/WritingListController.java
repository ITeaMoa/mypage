package com.iteamoa.mypage.controller;

import com.iteamoa.mypage.dto.FeedDto;
import com.iteamoa.mypage.service.WritingListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("writing")
public class WritingListController {
    private final WritingListService writingListService;

    @GetMapping
    public ResponseEntity<?> getWritingList(@RequestParam String creatorId, @RequestParam String sk) {
        List<FeedDto> FeedDTOs = writingListService.getWritingList(creatorId, sk);
        return ResponseEntity.ok(FeedDTOs);
    }

}
