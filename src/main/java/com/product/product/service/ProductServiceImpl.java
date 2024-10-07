package com.product.product.service;

import com.product.api.exception.ApiRuntimeException;
import com.product.api.response.pagination.CursorPaginationResponse;
import com.product.product.data.ProductListData;
import com.product.product.dto.ProductCreateDTO;
import com.product.product.dto.ProductListDTO;
import com.product.product.dto.ProductUpdateDTO;
import com.product.product.entity.Product;
import com.product.product.mapper.ProductMapper;
import com.product.product.query.ProductListQuery;
import com.product.product.repository.ProductQueryDslRepository;
import com.product.product.repository.ProductRepository;
import com.product.product.response.ProductListResponse;
import com.product.user.entity.User;
import com.product.user.service.UserService;
import com.product.util.KoreanUtil;
import com.product.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductQueryDslRepository productQueryDslRepository;
    private final UserService userService;
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public CursorPaginationResponse<List<ProductListResponse>> list(ProductListDTO dto) {

        List<ProductListData> list = productQueryDslRepository.list(productMapper.toListQuery(dto));
        List<ProductListResponse> dataList = productMapper.toListResponse(list);

        Long nextCursor = !list.isEmpty() ? dataList.getLast().productId() : null;
        boolean isNext = list.size() == dto.getLimit();

        return CursorPaginationResponse.of(dataList, nextCursor, isNext);
    }

    @Override
    @Transactional(readOnly = true)
    public <T> T findByProductId(Long productId, Class<T> clazz) {
        return productRepository.findByProductId(productId, clazz).orElseThrow(() -> new ApiRuntimeException("데이터가 존재하지 않습니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public <T> T findByProductIdAndUserUserId(Long productId, Long userId, Class<T> clazz) {
        return productRepository.findByProductIdAndUserUserId(productId, userId, clazz).orElseThrow(() -> new ApiRuntimeException("데이터가 존재하지 않습니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public <T> List<T> findByUserId(Long userId, Class<T> clazz){
        return productRepository.findByUserUserId(userId, clazz);
    }

    @Override
    public Product create(ProductCreateDTO dto) {
        User user = userService.get(dto.getUserId());
        String productNameInitials = KoreanUtil.extractInitials(dto.getProductName());
        return productRepository.insert(productMapper.toCreateEntity(dto, user, productNameInitials));

    }

    @Override
    public Product update(ProductUpdateDTO dto) {

        Product product = productRepository.findByProductIdAndUserUserId(dto.getProductId(), dto.getUserId(), Product.class)
                                           .orElseThrow(() -> new ApiRuntimeException("데이터가 존재하지 않습니다."));

        product.update(dto);

        return product;
    }

    @Override
    public void delete(Long productId, Long userId) {
        long deleteCnt = productQueryDslRepository.deleteByProductAndUserId(productId, userId);
        if(deleteCnt == 0){
            throw new ApiRuntimeException("삭제에 실패했습니다.");
        }
    }



}
