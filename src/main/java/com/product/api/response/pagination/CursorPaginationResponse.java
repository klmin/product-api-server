package com.product.api.response.pagination;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CursorPaginationResponse<T> {

    private T list;
    private Long nextCursor;
    private boolean hasNext;

    public static <T> CursorPaginationResponse<T> of(T list, Long nextCursor, boolean hasNext) {
        return CursorPaginationResponse.<T>builder()
                .list(list)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }

    public ApiCursorPaginationResponse toPaginationResponse(){
        return ApiCursorPaginationResponse.builder()
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }
}
