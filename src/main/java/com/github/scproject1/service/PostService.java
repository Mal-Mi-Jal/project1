package com.github.scproject1.service;

import com.github.scproject1.dto.PostRequestDto;
import com.github.scproject1.dto.PostResponseDto;
import com.github.scproject1.entity.Post;
import com.github.scproject1.entity.User;
import com.github.scproject1.repository.PostRepository;
import com.github.scproject1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createPost(PostRequestDto dto, String email){
        User user = userRepository.findById(email)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 유저입니다."));

        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(user)
                .build();

        return postRepository.save(post).getId();
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> findAllPosts(){
        return postRepository.findAllByOrderByIdDesc().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> findAllPostsByUserEmail(String email){
        return postRepository.findAllByUserEmailOrderByCreatedAtDesc(email).stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long updatePost(Long id, PostRequestDto dto, String email) {
        Post post = postRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다."));

        if(!post.getUser().getEmail().equals(email)){
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        post.update(dto.getTitle(), dto.getContent());

        return post.getId();
    }

    @Transactional
    public void deletePost(Long id, String email) {
        Post post = postRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다."));

        if(!post.getUser().getEmail().equals(email)){
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        postRepository.delete(post);
    }

}
