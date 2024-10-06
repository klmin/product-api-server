package com.product.rolehierarchy.response;

import java.time.LocalDateTime;

public record RoleHierarchyGetResponse
(
    String childId,
    String parentId,
    LocalDateTime regDttm
)
{
}
