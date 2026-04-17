package com.github.scproject1.controller;

import com.github.scproject1.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

// 아래 static import들이 있어야 get(), status() 등을 쓸 수 있어요!
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        // 아까 Controller에 만들어둔 /test 혹은 /api/posts/test 주소로 호출해보세요!
        mockMvc.perform(get("/api/posts/test"))
                .andExpect(status().isOk()) // 200 OK가 나오는지 확인
                .andDo(print());            // 결과물을 콘솔에 예쁘게 출력
    }
}