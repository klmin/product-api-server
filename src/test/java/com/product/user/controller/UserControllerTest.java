package com.product.user.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.product.api.response.ApiResponse;
import com.product.config.abstracts.AbstractMvcTest;
import com.product.config.security.WithMockUserCustom;
import com.product.user.response.UserDetailResponse;
import com.product.util.StringUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUserCustom
class UserControllerTest extends AbstractMvcTest {

    private static final String BASE_PATH = "/api/users";

    @Autowired
    private UserController userController;

    @BeforeEach
    void setUp() {
        setMockMvc(userController);
    }

    @Test
    void get() throws Exception {

        Long userId = 1L;

        String url = StringUtil.buildUrl(BASE_PATH, userId);

        ApiResponse<UserDetailResponse> response = getForObject(url, null, new TypeReference<>() {},
                status().isOk(),
                jsonPath("$.data.userId").value(userId));

        assertTrue(response.isResult());
        assertNotNull(response.getData());
        assertEquals(response.getData().userId(), userId);
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void changeStatus() {
    }

    @Test
    void delete() {
    }
}