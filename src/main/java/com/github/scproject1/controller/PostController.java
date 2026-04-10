package com.github.scproject1.controller;

import com.github.scproject1.dto.PostRequestDto;
import com.github.scproject1.dto.PostResponseDto;
import com.github.scproject1.entity.Post;
import com.github.scproject1.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Long> createPost(@RequestBody PostRequestDto dto) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Long postId = postService.createPost(dto, email);

        return ResponseEntity.ok(postId);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return ResponseEntity.ok(postService.findAllPosts());
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<PostResponseDto>> getAllPostsByUserEmail(@PathVariable String email) {
        List<PostResponseDto> posts = postService.findAllPostsByUserEmail(email);
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePost(@PathVariable Long id, @RequestBody PostRequestDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(postService.updatePost(id, dto, email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        postService.deletePost(id, email);
        return ResponseEntity.ok().build();
    }

}
