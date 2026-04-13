package com.github.scproject1.service;

import com.github.scproject1.entity.Like;
import com.github.scproject1.entity.Post;
import com.github.scproject1.entity.User;
import com.github.scproject1.repository.LikeRepository;
import com.github.scproject1.repository.PostRepository;
import com.github.scproject1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public boolean likePost(Long postId, String userEmail){
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new IllegalArgumentException("게시물이 없습니다."));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(()->new IllegalArgumentException("이메일이 없습니다."));

        Optional<Like> foundLike = likeRepository.findByPostAndUser(post, user);

        if(foundLike.isPresent()){
            likeRepository.delete(foundLike.get());
            return false;
        }else{
            Like like = Like.builder()
                    .post(post)
                    .user(user)
                    .build();
            likeRepository.save(like);
            return true;
        }
    }
}
