package com.product.log.service;


import com.product.log.dto.ApiLogCreateDTO;
import com.product.log.entity.ApiLog;
import com.product.log.mapper.LogMapper;
import com.product.log.repository.ApiLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApiLogServiceImpl implements ApiLogService {

    private final ApiLogRepository repository;
    private final LogMapper logMapper;

    @Override
    public ApiLog insert(ApiLogCreateDTO dto){
        return repository.insert(logMapper.toCreateEntity(dto));
    }

}
