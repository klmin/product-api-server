package com.product.log.dto;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class ApiLogCreateDTO {

    private String url;

    private String clientIp;

    private String contentType;

    private String httpMethod;

    private String queryString;

    private String requestBody;

    private String responseBody;

}
