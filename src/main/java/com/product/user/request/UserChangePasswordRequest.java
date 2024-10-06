package com.product.user.request;

import jakarta.validation.constraints.NotBlank;

public record UserChangePasswordRequest
(
    @NotBlank
    String oldPassword,
    @NotBlank
    String newPassword
)
{
}
