package com.product.config.abstracts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.objectmapper.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractMvcTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ObjectMapperUtil objectMapperUtil;


    public MvcResult performRequestJson(MockHttpServletRequestBuilder requestBuilder, String content, MultiValueMap<String, String> params) throws Exception {

        requestBuilder.contentType(MediaType.APPLICATION_JSON);

        if (content != null) {
            requestBuilder.content(content);
        }
        if (params != null) {
            requestBuilder.params(params);
        }

        return this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andReturn();
    }

    public MvcResult mvcResult(MockHttpServletRequestBuilder requestBuilder, String content, MultiValueMap<String, String> params) throws Exception {
        return performRequestJson(requestBuilder, content, params);
    }

    public MockHttpServletResponse mvcResultResponse(MockHttpServletRequestBuilder requestBuilder, String content, MultiValueMap<String, String> params) throws Exception {
        return mvcResult(requestBuilder, content, params).getResponse();
    }

    public String contentAsString(MockHttpServletRequestBuilder requestBuilder, String content, MultiValueMap<String, String> params) throws Exception {
        return mvcResultResponse(requestBuilder, content, params).getContentAsString();
    }

    public <T> T response(MockHttpServletRequestBuilder requestBuilder, String content, MultiValueMap<String, String> params, Class<T> clazz) throws Exception {
        return objectMapperUtil.readValue(contentAsString(requestBuilder, content, params),  clazz);
    }

    public <T> T response(MockHttpServletRequestBuilder requestBuilder, String content, MultiValueMap<String, String> params, TypeReference<T> clazz) throws Exception {
        String a = contentAsString(requestBuilder, content, params);
        System.out.println("a : "+a);
        return objectMapperUtil.readValue(a,  clazz);
    }

    public <T> T getForObject(String url, Object obj, Class<T> clazz) throws Exception{
        return response(get(url), null, multiValueMap(obj), clazz);
    }

    public <T> T postForObject(String url, Object obj, Class<T> clazz) throws Exception{
        return response(post(url), objectMapperUtil.writeValueAsString(obj), null, clazz);
    }

    public <T> T patchForObject(String url, Object obj, Class<T> clazz) throws Exception{
        return response(patch(url), objectMapperUtil.writeValueAsString(obj), null, clazz);
    }

    public <T> T getForObject(String url, Object obj, TypeReference<T> clazz) throws Exception{
        return response(get(url), null, multiValueMap(obj), clazz);
    }

    public <T> T postForObject(String url, Object obj, TypeReference<T> clazz) throws Exception{
        return response(post(url), objectMapperUtil.writeValueAsString(obj), null, clazz);
    }

    public <T> T patchForObject(String url, Object obj, TypeReference<T> clazz) throws Exception{
        return response(patch(url), objectMapperUtil.writeValueAsString(obj), null, clazz);
    }

    public <T> MultiValueMap<String, String> multiValueMap(T obj){
        Map<String, String> editMap = objectMapperUtil.convertValue(obj, new TypeReference<>() {});

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.setAll(editMap);

        return multiValueMap;
    }



}
