package com.product.log.service;

import com.product.log.dto.ApiLogCreateDTO;
import com.product.log.entity.ApiLog;


public interface ApiLogService {

   ApiLog insert(ApiLogCreateDTO request);

}
