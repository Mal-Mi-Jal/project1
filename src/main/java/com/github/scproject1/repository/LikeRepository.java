package com.github.scproject1.repository;


import com.github.scproject1.entity.Like;
import com.github.scproject1.entity.Post;
import com.github.scproject1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {
    Optional<Like> findByPostAndUser(Post post, User user);

    long countByPostId(Long postId);
}
