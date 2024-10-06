package com.product.product.service;

import com.product.api.exception.ApiRuntimeException;
import com.product.api.response.pagination.CursorPaginationResponse;
import com.product.config.security.WithMockUserCustom;
import com.product.product.dto.ProductCreateDTO;
import com.product.product.dto.ProductListDTO;
import com.product.product.dto.ProductUpdateDTO;
import com.product.product.entity.Product;
import com.product.product.enums.EnumProductCategory;
import com.product.product.enums.EnumProductSize;
import com.product.product.projection.ProductByUserIdProjection;
import com.product.product.response.ProductGetResponse;
import com.product.product.response.ProductListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@WithMockUserCustom
@Transactional
class ProductServiceImplTest {

    @Autowired
    ProductService productService;

    @Nested
    @DisplayName("상품 조회 리스트 서비스 테스트")
    class list{

        @DisplayName("상품 조회 리스트 서비스 테스트 성공")
        @Test
        void success(){

            ProductListDTO productListDTO = ProductListDTO.builder().userId(1L).lastCursorId(12L).searchKeyword("ㅇㅁㄹ").build();
            CursorPaginationResponse<List<ProductListResponse>> list = productService.list(productListDTO);

            assertNotNull(list);

            ProductListDTO initialProductListDTO = ProductListDTO.builder().userId(2L).lastCursorId(25L).searchKeyword("ㅇㅅ").build();
            CursorPaginationResponse<List<ProductListResponse>> initiallist = productService.list(initialProductListDTO);
            assertNotNull(initiallist);

        }

        @DisplayName("상품 조회 리스트 서비스 테스트 실패")
        @Test
        void fail(){

            ProductListDTO productListDTO = ProductListDTO.builder().userId(2L).lastCursorId(10000L).searchKeyword("아메리카노노").build();
            CursorPaginationResponse<List<ProductListResponse>> list = productService.list(productListDTO);

            assertNotNull(list);

            ProductListDTO initialProductListDTO = ProductListDTO.builder().userId(2L).lastCursorId(10000L).searchKeyword("ㅇㅁㄹㅋㄴㄴ").build();
            CursorPaginationResponse<List<ProductListResponse>> initiallist = productService.list(initialProductListDTO);
            assertNotNull(initiallist);
        }

    }

    @Nested
    @DisplayName("상품 조회 서비스 테스트")
    class findByProductIdAndUserUserId{

        @DisplayName("상품 조회 서비스 성공 테스트")
        @Test
        void success(){

            Long userId = 1L;
            Long productId = 1L;

            ProductGetResponse productGetResponse = productService.findByProductIdAndUserUserId(productId, userId, ProductGetResponse.class);

            assertEquals(productGetResponse.productId(), productId);
            assertEquals(productGetResponse.userUserId(), userId);
            assertEquals(4000, productGetResponse.price());

        }

        @DisplayName("상품 조회 서비스 실패 테스트")
        @Test
        void fail(){
            ApiRuntimeException findByProductIdFail = assertThrows(ApiRuntimeException.class,
                    () -> productService.findByProductId(99999L, ProductByUserIdProjection.class));

            assertEquals("데이터가 존재하지 않습니다.", findByProductIdFail.getMessage());

            ApiRuntimeException findByProductIdAndUserUserIdFail = assertThrows(ApiRuntimeException.class,
                    () -> productService.findByProductIdAndUserUserId(1L, 2L, ProductByUserIdProjection.class));

            assertEquals("데이터가 존재하지 않습니다.", findByProductIdAndUserUserIdFail.getMessage());
        }

    }

    @Nested
    @DisplayName("상품 등록 서비스 테스트")
    class create{

        @DisplayName("상품 등록 서비스 성공 테스트")
        @ParameterizedTest
        @MethodSource
        void success(Long userId, EnumProductCategory category, Integer price, Integer cost, String productName, String productNameInitials,
                     String description, String barcode, LocalDate expirationDate, EnumProductSize size) {

            int beforeSize = productService.findByUserId(userId, ProductByUserIdProjection.class).size();

            ProductCreateDTO productCreateDTO = buildProductCreateDTO(userId, category, price, cost, productName, description, barcode, expirationDate, size);
            Product product = productService.create(productCreateDTO);
            assertEquals(product.getUser().getUserId(), userId);
            assertEquals(product.getCategory(), category);
            assertEquals(product.getPrice(), price);
            assertEquals(product.getCost(), cost);
            assertEquals(product.getProductName(), productName);
            assertEquals(product.getProductNameInitials(), productNameInitials);
            assertEquals(product.getDescription(), description);
            assertEquals(product.getBarcode(), barcode);
            assertEquals(product.getExpirationDate(), expirationDate);
            assertEquals(product.getSize(), size);

            int afterSize = productService.findByUserId(userId, ProductByUserIdProjection.class).size();
            assertTrue(afterSize > beforeSize);

        }

        @DisplayName("상품 등록 서비스 실패 테스트")
        @ParameterizedTest
        @MethodSource
        void fail(Long userId, EnumProductCategory category, Integer price, Integer cost, String productName,
                  String description, String barcode, LocalDate expirationDate, EnumProductSize size, Class<? extends Throwable> clazz) {

            ProductCreateDTO productCreateDTO = buildProductCreateDTO(userId, category, price, cost, productName, description, barcode, expirationDate, size);

            assertThrows(clazz, () -> productService.create(productCreateDTO), "예외 발생 해야함.");

        }

        private static Stream<Arguments> success() {
            return Stream.of(
                    Arguments.of(2L, EnumProductCategory.COFFEE, 3000, 1000, "아이스 아메리카노", "ㅇㅇㅅ ㅇㅁㄹㅋㄴ",
                            "아메리카노", "121q23451", LocalDate.now(), EnumProductSize.SMALL),
                    Arguments.of(2L, EnumProductCategory.COFFEE, 4000, 1500, "슈크림 라떼", "ㅅㅋㄹ ㄹㄸ",
                            "라떼", "232ere1123", LocalDate.now(), EnumProductSize.SMALL),
                    Arguments.of(2L, EnumProductCategory.TEA, 5000, 2000, "아이스 캐모마일", "ㅇㅇㅅ ㅋㅁㅁㅇ",
                            "캐모마일", "1212323aa", LocalDate.now(), EnumProductSize.LARGE)
            );
        }

        private static Stream<Arguments> fail() {
            return Stream.of(
                    Arguments.of(99999L, EnumProductCategory.COFFEE, 3000, 1000, "아이스 아메리카노", "아메리카노", "1212323qq", LocalDate.now(), EnumProductSize.SMALL, JpaObjectRetrievalFailureException.class),
                    Arguments.of(2L, EnumProductCategory.COFFEE, 4000, 1500, "슈크림 라떼", "라떼", "34eae451", null, null, JpaSystemException.class),
                    Arguments.of(2L, null, 5000, 2000, "아이스 캐모마일", null, "test", LocalDate.now(), EnumProductSize.LARGE, JpaSystemException.class)
            );
        }

        public static ProductCreateDTO buildProductCreateDTO(Long userId, EnumProductCategory category, Integer price, Integer cost, String productName,
                                                       String description, String barcode, LocalDate expirationDate, EnumProductSize size) {
            return ProductCreateDTO.builder()
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
    @DisplayName("상품 수정 서비스 테스트")
    class update{

        @DisplayName("상품 수정 서비스 성공 테스트")
        @ParameterizedTest
        @MethodSource
        void success(Long productId, Long userId, EnumProductCategory category, Integer price, Integer cost, String productName, String productNameInitials,
                     String description, String barcode, LocalDate expirationDate, EnumProductSize size, Integer afterPrice){


            ProductUpdateDTO productUpdateDTO = buildProductUpdateDTO(productId, userId, category, price, cost, productName, description, barcode, expirationDate, size);
            Product product = productService.update(productUpdateDTO);
            Integer beforePrice = product.getPrice();

            assertEquals(product.getCategory(), category);
            assertEquals(product.getPrice(), price);
            assertEquals(product.getCost(), cost);
            assertEquals(product.getProductName(), productName);
            assertEquals(product.getProductNameInitials(), productNameInitials);
            assertEquals(product.getDescription(), description);
            assertEquals(product.getBarcode(), barcode);
            assertEquals(product.getExpirationDate(), expirationDate);
            assertEquals(product.getSize(), size);

            ProductUpdateDTO productUpdateDTOCategory = buildProductUpdateDTO(product.getProductId(), userId, null, afterPrice, null, null, null, null, null, null);

            product.update(productUpdateDTOCategory);

            assertEquals(product.getCategory(), category);
            assertEquals(product.getPrice(), afterPrice);
            assertNotEquals(beforePrice, afterPrice);
            assertEquals(product.getCost(), cost);
            assertEquals(product.getProductName(), productName);
            assertEquals(product.getProductNameInitials(), productNameInitials);
            assertEquals(product.getDescription(), description);
            assertEquals(product.getBarcode(), barcode);
            assertEquals(product.getExpirationDate(), expirationDate);
            assertEquals(product.getSize(), size);


        }

        @DisplayName("상품 수정 서비스 실패 테스트")
        @Test
        void fail(){

            Long userId = 1L;
            Long productId = 1L;
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i< 100; i++){
                sb.append("aa");
            }

            ProductUpdateDTO productUpdateDTO = buildProductUpdateDTO(productId, userId, null, null, null, null, null, sb.toString(), null, null);

            Product product = productService.update(productUpdateDTO);
            assertEquals(sb.toString(), product.getBarcode());

            assertThrows(DataIntegrityViolationException.class, () -> productService.update(productUpdateDTO), "예외발생해야함.");

        }

        private static Stream<Arguments> success() {
            return Stream.of(
                    Arguments.of(1L, 1L, EnumProductCategory.BAKERY, 5000, 1500, "호밀빵", "ㅎㅁㅃ",
                            "빵", "145131451", LocalDate.now(), EnumProductSize.SMALL, 5500),
                    Arguments.of( 2L, 1L, EnumProductCategory.COFFEE, 5000, 1500, "헤이즐넛", "ㅎㅇㅈㄴ",
                            "커피", "23e11423", LocalDate.now(), EnumProductSize.SMALL, 4000),
                    Arguments.of(3L, 1L, EnumProductCategory.DESSERT, 4000, 1000, "치즈케이크", "ㅊㅈㅋㅇㅋ",
                            "케이크", "1323aa", LocalDate.now(), EnumProductSize.SMALL, 3000)
            );
        }


        public static ProductUpdateDTO buildProductUpdateDTO(Long productId, Long userId, EnumProductCategory category, Integer price, Integer cost, String productName,
                                                             String description, String barcode, LocalDate expirationDate, EnumProductSize size) {
            return ProductUpdateDTO.builder()
                    .productId(productId)
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
    @DisplayName("상품 삭제 서비스 테스트")
    class delete{

        @DisplayName("상품 삭제 서비스 성공 테스트")
        @Test
        void success(){

            Long userId = 2L;

            ProductCreateDTO productCreateDTO = create.buildProductCreateDTO(userId, EnumProductCategory.COFFEE, 3000, 1000,
                    "아이스 아메리카노", "아메리카노", "12121212", LocalDate.now(), EnumProductSize.SMALL);

            List<ProductByUserIdProjection> initialAfterProjection = productService.findByUserId(userId, ProductByUserIdProjection.class);

            int initialAfterSize = initialAfterProjection.size();

            Product product = productService.create(productCreateDTO);

            List<ProductByUserIdProjection> createAfterProjection = productService.findByUserId(userId, ProductByUserIdProjection.class);

            int createAfterSize = createAfterProjection.size();

            productService.delete(product.getProductId(), userId);

            List<ProductByUserIdProjection> deleteAfterProjection = productService.findByUserId(userId, ProductByUserIdProjection.class);

            int deleteAfterSize = deleteAfterProjection.size();

            assertTrue(deleteAfterSize < createAfterSize);
            assertTrue(initialAfterSize == deleteAfterSize);

        }

        @DisplayName("상품 삭제 서비스 실패 테스트")
        @Test
        void fail(){

            ApiRuntimeException e = assertThrows(ApiRuntimeException.class, () -> productService.delete(99999L, 2L), "예외 발생해야함");
            assertEquals("삭제에 실패했습니다.", e.getMessage());

        }

    }

}
