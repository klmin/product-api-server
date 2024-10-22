package com.product.user.controller;


import com.product.api.response.ApiResponse;
import com.product.user.request.UserChangePasswordRequest;
import com.product.user.request.UserChangeStatusRequest;
import com.product.user.request.UserCreateRequest;
import com.product.user.request.UserUpdateRequest;
import com.product.user.response.UserDetailResponse;
import com.product.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDetailResponse>> get(@PathVariable Long id)  {
        var entity = service.findByUserId(id, UserDetailResponse.class);
        return ApiResponse.success(entity);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(@RequestBody @Valid UserCreateRequest request)  {
        service.create(request.toDTO());
        return ApiResponse.created();
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable Long userId, @RequestBody @Valid UserUpdateRequest request){
        service.update(request.toDTO(userId));
        return ApiResponse.success();
    }

    @PatchMapping("/{id}/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@PathVariable Long id, @RequestBody @Valid UserChangePasswordRequest request)  {
        service.changePassword(id, request.oldPassword(), request.newPassword());
        return ApiResponse.success();

    }

    @PatchMapping(value="/{id}/change-status")
    public ResponseEntity<ApiResponse<Void>> changeStatus(@PathVariable Long id, @RequestBody @Valid UserChangeStatusRequest request)  {
        service.changeStatus(id, request.status());
        return ApiResponse.success();
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id)  {
        service.delete(id);
        return ApiResponse.success();

    }

}
