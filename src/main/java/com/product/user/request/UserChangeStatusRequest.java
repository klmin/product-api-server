package com.product.user.request;

import com.product.user.enums.EnumUserStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserChangeStatusRequest
(
    @NotNull
    EnumUserStatus status
)
{
}
