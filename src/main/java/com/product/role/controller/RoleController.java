package com.product.role.controller;


import com.product.api.response.ApiResponse;
import com.product.role.response.RoleGetResponse;
import com.product.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/roles")
@Slf4j
public class RoleController {

    private final RoleService service;

    @GetMapping(value="/{id}")
    public ResponseEntity<ApiResponse<RoleGetResponse>> get(@PathVariable String id)  {
        RoleGetResponse response = service.findByRoleId(id, RoleGetResponse.class);
        return ApiResponse.success(response);
    }

}
