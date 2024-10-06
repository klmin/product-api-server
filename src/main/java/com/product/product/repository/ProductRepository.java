package com.product.product.repository;

import com.product.jpa.repository.GenericJpaRepository;
import com.product.product.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends GenericJpaRepository<Product, Long> {

    <T> Optional<T> findByProductId(Long id, Class<T> type);
    <T> Optional<T> findByProductIdAndUserUserId(Long productId, Long userId, Class<T> type);
    <T> List<T> findByUserUserId(Long userId, Class<T> type);

}
