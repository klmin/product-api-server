package com.product.userrole.controller;


import com.product.api.response.ApiResponse;
import com.product.userrole.response.UserRoleGetResponse;
import com.product.userrole.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-roles")
@RequiredArgsConstructor
@Slf4j
public class UserRoleController {

    private final UserRoleService service;

    @GetMapping(value="/{userId}/{roleId}")
    public ResponseEntity<ApiResponse<UserRoleGetResponse>> get(@PathVariable Long userId, @PathVariable String roleId)  {
        UserRoleGetResponse response = service.findByUserIdAndRoleId(userId, roleId, UserRoleGetResponse.class);
        return ApiResponse.success(response);
    }

}
