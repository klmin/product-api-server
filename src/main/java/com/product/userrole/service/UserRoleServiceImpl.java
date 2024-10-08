package com.product.userrole.service;


import com.product.api.exception.ApiRuntimeException;
import com.product.userrole.compositekey.UserRoleKey;
import com.product.userrole.dto.UserRoleCreateDto;
import com.product.userrole.entity.UserRole;
import com.product.userrole.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository repository;

    @Override
    public UserRole create(UserRoleCreateDto dto) {
        return repository.insert(
                UserRole.builder()
                        .userId(dto.getUserId())
                        .roleId(dto.getRoleId())
                        .build()
        );
    }

    @Override
    public void delete(UserRoleKey id) {
        repository.deleteOrThrow(id);
    }

    @Override
    @Transactional(readOnly = true)
    public <T> T findByUserIdAndRoleId(Long userId, String roleId, Class<T> type) {
        return repository.findByUserIdAndRoleId(userId, roleId, type).orElseThrow(() -> new ApiRuntimeException("데이터가 존재하지 않습니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public  <T> Collection<T> loadRoleByUserId(Long userId, Class<T> type) {

        Collection<T> list = repository.findByUserId(userId, type);

        if(list.isEmpty()){
            throw new ApiRuntimeException(HttpStatus.FORBIDDEN, "권한이 존재하지 않습니다.");
        }

        return list;

    }


}
