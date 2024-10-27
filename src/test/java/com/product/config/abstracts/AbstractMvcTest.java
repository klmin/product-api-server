package com.product.config.abstracts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.api.exception.handler.ApiExceptionHandler;
import com.product.messagesource.service.MessageSourceService;
import com.product.objectmapper.util.ObjectMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public abstract class AbstractMvcTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ObjectMapperUtil objectMapperUtil;

    @Autowired
    protected MessageSourceService messageSourceService;

    protected void setMockMvc(Object controller) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ApiExceptionHandler(messageSourceService))
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .alwaysDo(print())
                .build();
    }

    public MvcResult performRequestJsonAndResultMatcher(MockHttpServletRequestBuilder requestBuilder, String content, MultiValueMap<String, String> params, ResultMatcher... matchers)  {

        requestBuilder.contentType(MediaType.APPLICATION_JSON);

        if (content != null) {
            requestBuilder.content(content);
        }
        if (params != null) {
            requestBuilder.params(params);
        }

        ResultActions resultActions = null;

        try {
            resultActions = this.mockMvc.perform(requestBuilder);

            for (ResultMatcher matcher : matchers) {
                resultActions.andExpect(matcher);
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }

        return resultActions.andReturn();
    }

    public MvcResult performRequestJson(MockHttpServletRequestBuilder requestBuilder, String content, MultiValueMap<String, String> params, ResultMatcher... matchers) {
        return performRequestJsonAndResultMatcher(requestBuilder, content, params, matchers);
    }

    public MvcResult mvcResult(MockHttpServletRequestBuilder requestBuilder, String content, MultiValueMap<String, String> params, ResultMatcher... matchers) {
        return performRequestJson(requestBuilder, content, params, matchers);
    }

    public MockHttpServletResponse mvcResultResponse(MockHttpServletRequestBuilder requestBuilder, String content, MultiValueMap<String, String> params, ResultMatcher... matchers) {
        return mvcResult(requestBuilder, content, params, matchers).getResponse();
    }

    public String contentAsString(MockHttpServletRequestBuilder requestBuilder, String content, MultiValueMap<String, String> params, ResultMatcher... matchers) {
        try{
            return mvcResultResponse(requestBuilder, content, params, matchers).getContentAsString();
        }catch(UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }
    }

    public <T> T response(MockHttpServletRequestBuilder requestBuilder, String content, MultiValueMap<String, String> params, Class<T> clazz, ResultMatcher... matchers)  {
        return objectMapperUtil.readValue(contentAsString(requestBuilder, content, params, matchers), clazz);
    }

    public <T> T response(MockHttpServletRequestBuilder requestBuilder, String content, MultiValueMap<String, String> params, TypeReference<T> clazz, ResultMatcher... matchers)  {
        return objectMapperUtil.readValue(contentAsString(requestBuilder, content, params, matchers), clazz);
    }

    public <T> T getForObject(String url, Object obj, Class<T> clazz, ResultMatcher... matchers){
        return response(get(url), null, multiValueMap(obj), clazz, matchers);
    }

    public <T> T postForObject(String url, Object obj, Class<T> clazz, ResultMatcher... matchers) {
        return response(post(url), objectMapperUtil.writeValueAsString(obj), null, clazz, matchers);
    }

    public <T> T patchForObject(String url, Object obj, Class<T> clazz, ResultMatcher... matchers) {
        return response(patch(url), objectMapperUtil.writeValueAsString(obj), null, clazz, matchers);
    }

    public <T> T getForObject(String url, Object obj, TypeReference<T> clazz, ResultMatcher... matchers){
        return response(get(url), null, multiValueMap(obj), clazz, matchers);
    }

    public <T> T postForObject(String url, Object obj, TypeReference<T> clazz, ResultMatcher... matchers) {
        return response(post(url), objectMapperUtil.writeValueAsString(obj), null, clazz, matchers);
    }

    public <T> T patchForObject(String url, Object obj, TypeReference<T> clazz, ResultMatcher... matchers) {
        return response(patch(url), objectMapperUtil.writeValueAsString(obj), null, clazz, matchers);
    }

    public <T> T deleteById(String url, Object id, TypeReference<T> clazz, ResultMatcher... matchers){
        return response(delete(url, id), null, null, clazz, matchers);
    }

    public <T> MultiValueMap<String, String> multiValueMap(T obj){

        if(obj == null){
            return null;
        }

        Map<String, String> editMap = objectMapperUtil.convertValue(obj, new TypeReference<>() {});

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.setAll(editMap);

        return multiValueMap;
    }



}
