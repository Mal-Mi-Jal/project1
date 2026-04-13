package com.github.scproject1.controller;

import com.github.scproject1.dto.CommentRequestDto;
import com.github.scproject1.dto.CommentResponseDto;
import com.github.scproject1.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long id){
        List<CommentResponseDto> comments = commentService.getCommentsByPostId(id);

        return ResponseEntity.ok(comments);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<Void> updateComments(@PathVariable Long id,
                                               @RequestBody CommentRequestDto dto){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        commentService.updateComment(id, dto.getContent(), email);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComments(@PathVariable Long id){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        commentService.deleteComment(id, email);

        return ResponseEntity.ok().build();
    }

}
