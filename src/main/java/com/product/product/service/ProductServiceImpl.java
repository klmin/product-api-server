package com.product.product.service;

import com.product.api.exception.ApiRuntimeException;
import com.product.api.response.pagination.CursorPaginationResponse;
import com.product.product.data.ProductListData;
import com.product.product.dto.ProductCreateDto;
import com.product.product.dto.ProductListDto;
import com.product.product.dto.ProductUpdateDto;
import com.product.product.entity.Product;
import com.product.product.mapper.ProductMapper;
import com.product.product.repository.ProductQueryDslRepository;
import com.product.product.repository.ProductRepository;
import com.product.product.response.ProductResponse;
import com.product.user.entity.User;
import com.product.user.service.UserService;
import com.product.util.KoreanUtil;
import com.product.util.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public CursorPaginationResponse<List<ProductResponse>> list(ProductListDto dto) {

        List<ProductListData> list = productQueryDslRepository.list(productMapper.toListQuery(dto));
        List<ProductResponse> dataList = productMapper.toListResponse(list);

        Long nextCursor = !list.isEmpty() ? dataList.getLast().productId() : null;
        boolean isNext = list.size() == dto.getLimit();

        return CursorPaginationResponse.of(dataList, nextCursor, isNext);
    }

    @Override
    @Transactional(readOnly = true)
    public <T> T findByProductId(Long productId, Class<T> clazz) {
        return productRepository.findByProductId(productId, clazz).orElseThrow(() -> new ApiRuntimeException(MessageConstants.NO_DATA_MESSAGE));
    }

    @Override
    @Transactional(readOnly = true)
    public <T> T findByProductIdAndUserUserId(Long productId, Long userId, Class<T> clazz) {
        return productRepository.findByProductIdAndUserUserId(productId, userId, clazz).orElseThrow(() -> new ApiRuntimeException(MessageConstants.NO_DATA_MESSAGE));
    }

    @Override
    @Transactional(readOnly = true)
    public <T> List<T> findByUserId(Long userId, Class<T> clazz){
        return productRepository.findByUserUserId(userId, clazz);
    }

    @Override
    public Product create(ProductCreateDto dto) {
        User user = userService.get(dto.getUserId());
        String productNameInitials = KoreanUtil.extractInitials(dto.getProductName());
        return productRepository.insert(productMapper.toCreateEntity(dto, user, productNameInitials));

    }

    @Override
    public Product update(ProductUpdateDto dto) {

        Product product = productRepository.findByProductIdAndUserUserId(dto.getProductId(), dto.getUserId(), Product.class)
                                           .orElseThrow(() -> new ApiRuntimeException(MessageConstants.NO_DATA_MESSAGE));

        product.update(dto);

        return product;
    }

    @Override
    public void delete(Long productId, Long userId) {
        long deleteCnt = productQueryDslRepository.deleteByProductAndUserId(productId, userId);
        if(deleteCnt == 0){
            throw new ApiRuntimeException(MessageConstants.DELETE_FAIL_MESSAGE);
        }
    }



}
