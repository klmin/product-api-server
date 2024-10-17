package com.product.async.service;


import com.product.log.dto.ApiLogCreateDTO;

public interface AsyncService {

    void insertApiLogAsync(ApiLogCreateDTO dto);

}
