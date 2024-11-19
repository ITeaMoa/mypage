package com.iteamoa.mypage.controller;

import com.iteamoa.mypage.dto.ApplicationDto;
import com.iteamoa.mypage.dto.FeedDto;
import com.iteamoa.mypage.service.WritingListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/writing")
public class WritingListController {
    private final WritingListService writingListService;

    @GetMapping
    public ResponseEntity<?> getWritingList(@RequestParam String creatorId, @RequestParam String sk) {
        return ResponseEntity.ok(writingListService.getWritingList(creatorId, sk));
    }

    @GetMapping("/application")
    public ResponseEntity<?> getApplication(@RequestParam String feedId) {
        return ResponseEntity.ok(writingListService.getApplicationList(feedId, null));
    }

    @GetMapping("/part")
    public ResponseEntity<?> getApplicationByRole(@RequestParam String feedId, @RequestParam String part) {
        return ResponseEntity.ok(writingListService.getApplicationList(feedId, part));
    }

    @PatchMapping("/accept")
    public ResponseEntity<?> acceptApplication(@RequestBody ApplicationDto applicationDto) {
        try {
            writingListService.acceptApplication(applicationDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/reject")
    public ResponseEntity<?> rejectApplication(@RequestBody ApplicationDto applicationDto) {
        try{
            writingListService.rejectApplication(applicationDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/cancel")
    public ResponseEntity<?> cancelApplication(@RequestBody ApplicationDto applicationDto) {
        try{
            writingListService.cancelApplication(applicationDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/close")
    public ResponseEntity<?> closeFeed(@RequestBody FeedDto feedDto) {
        try{
            writingListService.closeFeed(feedDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestParam String userId) {
        return ResponseEntity.ok(writingListService.getUserProfile(userId));
    }

}
