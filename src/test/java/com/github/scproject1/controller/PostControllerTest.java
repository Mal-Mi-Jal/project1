package com.github.scproject1.controller;

import com.github.scproject1.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @Test
    @WithMockUser
    @DisplayName("Mock API 호출 테스트")
    void getMockTest() throws Exception {
        mockMvc.perform(get("/api/posts/test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())               // 배열인지
                .andExpect(jsonPath("$[0].title").value("첫 번째 글"))  // 첫번째 title
                .andExpect(jsonPath("$[1].writerEmail").value("user2@test.com")) // 두번째 이메일
                .andDo(print());
    }
}