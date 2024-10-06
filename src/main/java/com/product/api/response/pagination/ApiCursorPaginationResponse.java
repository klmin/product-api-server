package com.product.api.response.pagination;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiCursorPaginationResponse {

    private Long nextCursor;
    private boolean hasNext;

}
