package com.product.rolehierarchy.controller;


import com.product.api.response.ApiResponse;
import com.product.rolehierarchy.response.RoleHierarchyGetResponse;
import com.product.rolehierarchy.service.RoleHierarchyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/rolehierarchies")
public class RoleHierarchyController {

    private final RoleHierarchyService service;

    @GetMapping("/child/{childId}/parent/{parentId}")
    public ResponseEntity<ApiResponse<RoleHierarchyGetResponse>> get(@PathVariable String childId, @PathVariable String parentId)  {
        RoleHierarchyGetResponse response = service.findByChildIdAndParentId(childId, parentId, RoleHierarchyGetResponse.class);
        return ApiResponse.success(response);
    }

}
