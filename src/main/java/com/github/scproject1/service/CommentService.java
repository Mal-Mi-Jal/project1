package com.github.scproject1.service;

import com.github.scproject1.dto.CommentRequestDto;
import com.github.scproject1.entity.Comment;
import com.github.scproject1.entity.Post;
import com.github.scproject1.entity.User;
import com.github.scproject1.repository.CommentRepository;
import com.github.scproject1.repository.PostRepository;
import com.github.scproject1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveComment(CommentRequestDto dto, String email){
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(()->new IllegalArgumentException("게시글이 없습니다."));

        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new IllegalArgumentException("사용자가 없습니다"));

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .post(post)
                .user(user)
                .build();

        commentRepository.save(comment);
    }


}
