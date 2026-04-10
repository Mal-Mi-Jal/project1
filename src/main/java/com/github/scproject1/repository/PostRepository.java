package com.github.scproject1.repository;

import com.github.scproject1.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    // 1. 특정 유저가 작성한 글만 모아보기
    List<Post> findAllByUserEmail(String email);

    List<Post> findAllByUserEmailOrderByCreatedAtDesc(String email);

    // 2. 제목에 특정 단어가 포함된 글 검색하기 (검색 기능)
    List<Post> findByTitleContaining(String keyword);

    // 3. 최신순으로 정렬해서 가져오기 (보통 id 내림차순)
    List<Post> findAllByOrderByIdDesc();

}
