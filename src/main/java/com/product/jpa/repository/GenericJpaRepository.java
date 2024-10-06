package com.product.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface GenericJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    T get(ID id);
    T getOrThrowMessage(ID id, String msg);

    T getOrNull(ID id);
    void deleteOrThrow(ID id);

    T insert(T entity);



}
