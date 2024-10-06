package com.product.userrole.response;

import java.time.LocalDateTime;

public record UserRoleGetResponse(
        Long userId,
    String roleId,
    LocalDateTime regDttm
) {
}
