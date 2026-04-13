package com.github.scproject1.controller;

import com.github.scproject1.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/posts/{id}/likes")
    public ResponseEntity<String> likePost(@PathVariable Long id){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isLiked = likeService.likePost(id, email);

        if(isLiked){
            return ResponseEntity.status(HttpStatus.CREATED).body("좋아요 등록!");
        } else {
            return ResponseEntity.ok("좋아요 취소!");
        }
    }

}
