package com.product.log.filter;

import com.product.async.service.AsyncService;
import com.product.log.dto.ApiLogCreateDTO;
import com.product.log.filter.wrapper.CacheHttpServletRequest;
import com.product.util.IpUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class ApiLoggingFilter extends OncePerRequestFilter {

    private final AsyncService asyncService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("## ApiLoggingFilter doFilterInternal ##");
        CacheHttpServletRequest cacheHttpServletRequest = new CacheHttpServletRequest(request);
        ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response);

        ApiLogCreateDTO dto = logRequest(cacheHttpServletRequest);
        filterChain.doFilter(cacheHttpServletRequest, contentCachingResponseWrapper);

        dto = logResponse(contentCachingResponseWrapper, dto);
        asyncService.insertApiLogAsync(dto);
        contentCachingResponseWrapper.copyBodyToResponse();

    }

    private ApiLogCreateDTO logRequest(CacheHttpServletRequest request) {

        String clientIp = IpUtil.getClientIp(request);
        log.info("[Request Client IP] : {}", clientIp);

        String contentType = request.getContentType();
        log.info("[Request ContentType] : {}", contentType);

        String url = request.getRequestURL().toString();
        log.info("[Request URL] : {}", url);

        String httpMethod = request.getMethod();
        log.info("[Request Method] : {}", httpMethod);

        String queryString = request.getQueryString();
        if (StringUtils.hasText(queryString)) {
            log.info("[Request QueryString] : {}", queryString);
        }

        String requestBody = request.getBody();

        if(StringUtils.hasText(requestBody)){
            log.info("[Request Body] : {}", requestBody);
        }

        return ApiLogCreateDTO.builder()
                    .url(url)
                    .clientIp(clientIp)
                    .contentType(contentType)
                    .httpMethod(httpMethod)
                    .queryString(queryString)
                    .requestBody(requestBody)
                    .build();

    }

    private ApiLogCreateDTO logResponse(ContentCachingResponseWrapper response, ApiLogCreateDTO dto) {

        int status = response.getStatus();
        log.info("[Response Status] : {}", status);

        String responseBody = new String(response.getContentAsByteArray());
        log.info("[Response Body] : {}", responseBody);

        return dto.toBuilder()
                .responseBody(responseBody)
                .build();

    }

}
