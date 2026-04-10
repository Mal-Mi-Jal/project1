package com.github.scproject1.controller;

import com.github.scproject1.dto.CommentRequestDto;
import com.github.scproject1.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<Void> createComments(@RequestBody CommentRequestDto dto){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        commentService.saveComment(dto, email);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
