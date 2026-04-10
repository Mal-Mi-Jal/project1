package com.github.scproject1.dto;

import com.github.scproject1.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String writerEmail;
    private String createdAt;
    private String updatedAt;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.writerEmail = post.getUser().getEmail();
        this.createdAt = post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // updatedAt이 null이 아닐 때만 포맷팅 (처음엔 null일 수 있음)
        if (post.getUpdatedAt() != null) {
            this.updatedAt = post.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }


    }


}
