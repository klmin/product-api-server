package com.product.product.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.product.config.abstracts.AbstractMvcTest;
import com.product.config.security.WithMockUserCustom;
import com.product.messagesource.service.MessageSourceService;
import com.product.product.dto.ProductCreateDto;
import com.product.product.entity.Product;
import com.product.product.enums.EnumProductCategory;
import com.product.product.enums.EnumProductSize;
import com.product.product.request.ProductCreateRequest;
import com.product.product.request.ProductListRequest;
import com.product.product.request.ProductUpdateRequest;
import com.product.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Stream;

import static com.product.util.MessageConstants.NO_DATA_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUserCustom
@Transactional
class ProductControllerTest extends AbstractMvcTest {

    @Autowired
    ProductService productService;
    @Autowired
    MessageSourceService messageSourceService;

    @Nested
    @DisplayName("상품 리스트 컨트롤러 테스트")
    class list{

        @DisplayName("상품 리스트 컨트롤러 성공 테스트")
        @Test
        void success() throws Exception{

            HttpStatus status = HttpStatus.OK;

            ProductListRequest productListRequest = ProductListRequest.builder().lastCursorId(100L).searchKeyword("ㅋㄹ").build();

            Map<String, String> editMap = objectMapper.convertValue(productListRequest, new TypeReference<>() {});

            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
            multiValueMap.setAll(editMap);

            mockMvc.perform(get("/api/products")
                            .params(multiValueMap)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(status.value()))
                    .andExpect(jsonPath("$.code").value(status.value()))
                    .andExpect(jsonPath("$.message").value(status.getReasonPhrase()))
                    .andExpect(jsonPath("$.data[0]").isNotEmpty())
                    .andExpect(jsonPath("$.page.nextCursor").isNotEmpty())
            ;

        }

        @DisplayName("상품 리스트 컨트롤러 실패 테스트")
        @Test
        void fail() throws Exception{

            HttpStatus status = HttpStatus.OK;

            ProductListRequest productListRequest = ProductListRequest.builder().lastCursorId(10000L).limit(10).searchKeyword("아메리카노노").build();

            Map<String, String> editMap = objectMapper.convertValue(productListRequest, new TypeReference<>() {});

            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
            multiValueMap.setAll(editMap);

            mockMvc.perform(get("/api/products")
                            .params(multiValueMap)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(status.value()))
                    .andExpect(jsonPath("$.code").value(status.value()))
                    .andExpect(jsonPath("$.message").value(status.getReasonPhrase()))
                    .andExpect(jsonPath("$.data").isEmpty())
                    .andExpect(jsonPath("$.page.nextCursor").isEmpty())
                    .andExpect(jsonPath("$.page.hasNext").value(false))

            ;


        }

    }

    @Nested
    @DisplayName("상품 조회 컨트롤러 테스트")
    class get{

        @DisplayName("상품 조회 컨트롤러 성공 테스트")
        @Test
        void success() throws Exception {

            Long userId = 1L;
            HttpStatus status = HttpStatus.OK;

            ProductCreateDto productCreateDTO = buildProductCreateDTO(userId, EnumProductCategory.TEA, 3000, 1000,
                    "아이스티", "ㅇㅇㅅㅌ", "4aa5646547", LocalDate.now(), EnumProductSize.SMALL);

            Product product = productService.create(productCreateDTO);

            mockMvc.perform(get("/api/products/"+product.getProductId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(status.value()))
                    .andExpect(jsonPath("$.code").value(status.value()))
                    .andExpect(jsonPath("$.message").value(status.getReasonPhrase()));

        }

        @DisplayName("상품 조회 컨트롤러 실패 테스트")
        @Test
        void fail() throws Exception{

            HttpStatus status = HttpStatus.BAD_REQUEST;

            mockMvc.perform(get("/api/products/aa11")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(status.value()))
                    .andExpect(jsonPath("$.code").value(status.value()))
                    .andExpect(jsonPath("$.message").value(status.getReasonPhrase()));

            mockMvc.perform(get("/api/products/999999")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(status.value()))
                    .andExpect(jsonPath("$.code").value(status.value()))
                    .andExpect(jsonPath("$.message").value(messageSourceService.getMessage(NO_DATA_FOUND)));
        }


        public static ProductCreateDto buildProductCreateDTO(Long userId, EnumProductCategory category, Integer price, Integer cost, String productName,
                                                             String description, String barcode, LocalDate expirationDate, EnumProductSize size) {
            return ProductCreateDto.builder()
                    .userId(userId)
                    .category(category)
                    .price(price)
                    .cost(cost)
                    .productName(productName)
                    .description(description)
                    .barcode(barcode)
                    .expirationDate(expirationDate)
                    .size(size)
                    .build();
        }

    }



    @Nested
    @DisplayName("상품 등록 컨트롤러 테스트")
    class create{

        @DisplayName("상품 등록 컨트롤러 성공 테스트")
        @ParameterizedTest
        @MethodSource("success")
        void success(EnumProductCategory category, Integer price, Integer cost, String productName,
                     String description, String barcode, LocalDate expirationDate, EnumProductSize size) throws Exception {

            HttpStatus httpStatus = HttpStatus.CREATED;
            ProductCreateRequest productCreateRequest = buildProductCreateRequest(category, price, cost, productName, description, barcode, expirationDate, size);

            mockMvc.perform(post("/api/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(productCreateRequest))
                    )
                    .andDo(print())
                    .andExpect(status().is(httpStatus.value()))
                    .andExpect(jsonPath("$.code").value(httpStatus.value()))
                    .andExpect(jsonPath("$.message").value(httpStatus.getReasonPhrase()))
            ;

        }

        @DisplayName("상품 등록 컨트롤러 실패 테스트")
        @Test
        void fail() throws Exception {

            HttpStatus status = HttpStatus.BAD_REQUEST;

            ProductCreateRequest productCreateRequest = buildProductCreateRequest(null, null, null, "아이스 아메리카노",
                    "아메리카노", "121q23451", LocalDate.now(), EnumProductSize.SMALL);

            mockMvc.perform(post("/api/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(productCreateRequest))
                    )
                    .andDo(print())
                    .andExpect(status().is(status.value()))
                    .andExpect(jsonPath("$.code").value(status.value()))
                    .andExpect(jsonPath("$.message").value("필수값이 누락되었습니다."))
            ;

            productCreateRequest = buildProductCreateRequest(EnumProductCategory.BAKERY, 3000, 2100, "아이스 아메리카노",
                    "아메리카노", "121q23451", LocalDate.now(), null);

            mockMvc.perform(post("/api/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(productCreateRequest))
                    )
                    .andDo(print())
                    .andExpect(status().is(status.value()))
                    .andExpect(jsonPath("$.code").value(status.value()))
                    .andExpect(jsonPath("$.message").value("필수값이 누락되었습니다."))
            ;

        }

        private static Stream<Arguments> success() {
            return Stream.of(
                    Arguments.of(EnumProductCategory.COFFEE, 3000, 1000, "아이스 아메리카노",
                            "아메리카노", "121q23451", LocalDate.now(), EnumProductSize.SMALL),
                    Arguments.of(EnumProductCategory.COFFEE, 4000, 1500, "슈크림 라떼",
                            "라떼", "232ere1123", LocalDate.now(), EnumProductSize.SMALL),
                    Arguments.of(EnumProductCategory.TEA, 5000, 2000, "아이스 캐모마일",
                            "캐모마일", "1212323aa", LocalDate.now(), EnumProductSize.LARGE)
            );
        }

        public static ProductCreateRequest buildProductCreateRequest(EnumProductCategory category, Integer price, Integer cost, String productName,
                                                                 String description, String barcode, LocalDate expirationDate, EnumProductSize size) {
            return ProductCreateRequest.builder()
                    .category(category)
                    .price(price)
                    .cost(cost)
                    .productName(productName)
                    .description(description)
                    .barcode(barcode)
                    .expirationDate(expirationDate)
                    .size(size)
                    .build();
        }


    }


    @Nested
    @DisplayName("상품 수정 컨트롤러 테스트")
    class update{

        @DisplayName("상품 수정 컨트롤러 성공 테스트")
        @ParameterizedTest
        @MethodSource("success")
        @WithMockUserCustom(userId=1L)
        void success(EnumProductCategory category, Integer price, Integer cost, String productName,
                     String description, String barcode, LocalDate expirationDate, EnumProductSize size) throws Exception {

            Long userId = 1L;
            HttpStatus status = HttpStatus.OK;

            ProductCreateDto productCreateDTO = get.buildProductCreateDTO(userId, EnumProductCategory.COFFEE, 3000, 1000,
                    "아이스티", "ㅇㅇㅅㅌ", "456411qq", LocalDate.now(), EnumProductSize.SMALL);

            Product product = productService.create(productCreateDTO);

            ProductUpdateRequest productUpdateRequest = buildProductUpdateRequest(category, price, cost, productName, description, barcode, expirationDate, size);

            mockMvc.perform(patch("/api/products/"+product.getProductId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(productUpdateRequest)))
                    .andDo(print())
                    .andExpect(status().is(status.value()))
                    .andExpect(jsonPath("$.code").value(status.value()))
                    .andExpect(jsonPath("$.message").value(status.getReasonPhrase()));

        }

        @DisplayName("상품 수정 컨트롤러 실패 테스트")
        @Test
        @WithMockUserCustom(userId=2L)
        void fail() throws Exception {

            HttpStatus status = HttpStatus.BAD_REQUEST;

            ProductUpdateRequest productUpdateRequest = buildProductUpdateRequest(null, 5000, 2000, "아이스 아메리카노",
                    "아메리카노", "33333122", LocalDate.now(), EnumProductSize.SMALL);

            mockMvc.perform(patch("/api/products/2")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(productUpdateRequest)))
                    .andDo(print())
                    .andExpect(status().is(status.value()))
                    .andExpect(jsonPath("$.code").value(status.value()));

        }

        private static Stream<Arguments> success() {
            return Stream.of(
                    Arguments.of(EnumProductCategory.BAKERY, null, 1500, "호밀빵",
                            "빵", "145131451", LocalDate.now(), EnumProductSize.SMALL, 5500),
                    Arguments.of( EnumProductCategory.COFFEE, 5000, 1500, null,
                            "커피", "23e11423", LocalDate.now(), EnumProductSize.SMALL, 4000),
                    Arguments.of(EnumProductCategory.DESSERT, 4000, 1000, "치즈케이크",
                            "케이크", null, LocalDate.now(), EnumProductSize.SMALL, 3000)
            );
        }

        public static ProductUpdateRequest buildProductUpdateRequest(EnumProductCategory category, Integer price, Integer cost, String productName,
                                                                 String description, String barcode, LocalDate expirationDate, EnumProductSize size) {
            return ProductUpdateRequest.builder()
                    .category(category)
                    .price(price)
                    .cost(cost)
                    .productName(productName)
                    .description(description)
                    .barcode(barcode)
                    .expirationDate(expirationDate)
                    .size(size)
                    .build();
        }

    }



    @Nested
    @DisplayName("상품 삭제 컨트롤러 테스트")
    class delete{

        @Test
        @DisplayName("상품 삭제 컨트롤러 성공 테스트")
        @WithMockUserCustom(userId=1L)
        void success() throws Exception {

            HttpStatus status = HttpStatus.OK;

            mockMvc.perform(delete("/api/products/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(status.value()))
                    .andExpect(jsonPath("$.code").value(status.value()))
                    .andExpect(jsonPath("$.message").value(status.getReasonPhrase()));

        }

        @Test
        @DisplayName("상품 삭제 컨트롤러 실패 테스트")
        @WithMockUserCustom(userId=1L)
        void fail() throws Exception {

            HttpStatus status = HttpStatus.BAD_REQUEST;

            mockMvc.perform(delete("/api/products/20")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(status.value()))
                    .andExpect(jsonPath("$.code").value(status.value()))
                    .andExpect(jsonPath("$.message").value("삭제에 실패했습니다."));

        }
    }
}