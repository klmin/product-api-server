package com.product.rolehierarchy.service;


import com.product.api.exception.ApiRuntimeException;
import com.product.rolehierarchy.dto.RoleHierarchyListDto;
import com.product.rolehierarchy.repository.RoleHierarchyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class RoleHierarchyServiceImpl implements RoleHierarchyService{

    private final RoleHierarchyRepository repository;

    @Override
    public String getRoleHierarchyString(){
        return repository.findAllBy(RoleHierarchyListDto.class).stream()
                .map(hierarchy -> hierarchy.childId() + " > " + hierarchy.parentId())
                .collect(Collectors.joining("\n"));
    }

    @Override
    @Transactional(readOnly = true)
    public <T> T findByChildIdAndParentId(String childId, String parentId, Class<T> type) {
        return repository.findByChildIdAndParentId(childId, parentId, type).orElseThrow(() -> new ApiRuntimeException("데이터가 존재하지 않습니다."));
    }


}
