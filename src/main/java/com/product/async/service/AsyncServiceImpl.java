package com.product.async.service;


import com.product.log.dto.ApiLogCreateDTO;
import com.product.log.service.ApiLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AsyncServiceImpl implements AsyncService {

    private final ApiLogService apiLogService;

    @Async
    public void insertApiLogAsync(ApiLogCreateDTO dto){
        apiLogService.insert(dto);
    }



}
