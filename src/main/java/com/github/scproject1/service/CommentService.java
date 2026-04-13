package com.github.scproject1.service;

import com.github.scproject1.dto.CommentRequestDto;
import com.github.scproject1.dto.CommentResponseDto;
import com.github.scproject1.entity.Comment;
import com.github.scproject1.entity.Post;
import com.github.scproject1.entity.User;
import com.github.scproject1.repository.CommentRepository;
import com.github.scproject1.repository.PostRepository;
import com.github.scproject1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByPostId(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new IllegalArgumentException("해당 게시글이 없습니다.");
        }

        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateComment(Long id, String content, String email){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 댓글이 없습니다."));

        if(!comment.getUser().getEmail().equals(email)){
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        comment.update(content);
    }

    @Transactional
    public void deleteComment(Long id, String email){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 댓글이 없습니다."));
        
        if(!comment.getUser().getEmail().equals(email)){
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);

    }


}
