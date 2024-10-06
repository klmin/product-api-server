package com.product.jpa.repository;

import com.product.api.exception.ApiRuntimeException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.io.Serializable;

public class GenericJpaRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements GenericJpaRepository<T, ID> {

    private final EntityManager entityManager;
    public GenericJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public T get(ID id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("[EntityNotFoundException] id : "+id));
    }

    @Override
    public T getOrThrowMessage(ID id, String msg) {
        return findById(id).orElseThrow(() -> new ApiRuntimeException(msg));
    }

    @Override
    public T getOrNull(ID id) {
        return findById(id).orElse(null);
    }

    @Override
    public void deleteOrThrow(ID id) {
        super.delete(this.getOrThrowMessage(id, "삭제에 실패했습니다."));
    }

    @Override
    public T insert(T entity) {
        this.entityManager.persist(entity);
        return entity;
    }


}
