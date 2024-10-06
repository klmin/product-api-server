package com.product.role.service;


import com.product.api.exception.ApiRuntimeException;
import com.product.role.entity.Role;
import com.product.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    private final RoleRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Role get(String id) {
        return repository.get(id);
    }


    @Override
    @Transactional(readOnly = true)
    public <T> T findByRoleId(String id, Class<T> type) {
        return repository.findByRoleId(id, type).orElseThrow(() -> new ApiRuntimeException("데이터가 존재하지 않습니다."));
    }
}
