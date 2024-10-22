package com.product.config.abstracts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.api.exception.handler.ApiExceptionHandler;
import com.product.messagesource.service.MessageSourceService;
import com.product.objectmapper.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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

    @Autowired
    protected MessageSourceService messageSourceService;

    protected void setMockMvc(Object controller) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ApiExceptionHandler(messageSourceService))
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .alwaysDo(print())
                .build();
    }

    public MvcResult performRequestJsonAndResultMatcher(MockHttpServletRequestBuilder requestBuilder, String content, MultiValueMap<String, String> params, ResultMatcher... matchers) throws Exception {

        requestBuilder.contentType(MediaType.APPLICATION_JSON);

        if (content != null) {
            requestBuilder.content(content);
        }
        if (params != null) {
            requestBuilder.params(params);
        }

        var resultActions = this.mockMvc.perform(requestBuilder);

        for (ResultMatcher matcher : matchers) {
            resultActions.andExpect(matcher);
        }

        return resultActions.andReturn();
    }

    public MvcResult performRequestJson(MockHttpServletRequestBuilder requestBuilder, String content, MultiValueMap<String, String> params, ResultMatcher... matchers) throws Exception {
        return performRequestJsonAndResultMatcher(requestBuilder, content, params, matchers);
    }

    public MvcResult mvcResult(MockHttpServletRequestBuilder requestBuilder, String content, MultiValueMap<String, String> params, ResultMatcher... matchers) throws Exception {
        return performRequestJson(requestBuilder, content, params, matchers);
    }

    public MockHttpServletResponse mvcResultResponse(MockHttpServletRequestBuilder requestBuilder, String content, MultiValueMap<String, String> params, ResultMatcher... matchers) throws Exception {
        return mvcResult(requestBuilder, content, params, matchers).getResponse();
    }

    public String contentAsString(MockHttpServletRequestBuilder requestBuilder, String content, MultiValueMap<String, String> params, ResultMatcher... matchers) throws Exception {
        return mvcResultResponse(requestBuilder, content, params, matchers).getContentAsString();
    }

    public <T> T response(MockHttpServletRequestBuilder requestBuilder, String content, MultiValueMap<String, String> params, Class<T> clazz, ResultMatcher... matchers) throws Exception {
        return objectMapperUtil.readValue(contentAsString(requestBuilder, content, params, matchers), clazz);
    }

    public <T> T response(MockHttpServletRequestBuilder requestBuilder, String content, MultiValueMap<String, String> params, TypeReference<T> clazz, ResultMatcher... matchers) throws Exception {
        return objectMapperUtil.readValue(contentAsString(requestBuilder, content, params, matchers), clazz);
    }

    public <T> T getForObject(String url, Object obj, Class<T> clazz, ResultMatcher... matchers) throws Exception{
        return response(get(url), null, multiValueMap(obj), clazz, matchers);
    }

    public <T> T postForObject(String url, Object obj, Class<T> clazz, ResultMatcher... matchers) throws Exception{
        return response(post(url), objectMapperUtil.writeValueAsString(obj), null, clazz, matchers);
    }

    public <T> T patchForObject(String url, Object obj, Class<T> clazz, ResultMatcher... matchers) throws Exception{
        return response(patch(url), objectMapperUtil.writeValueAsString(obj), null, clazz, matchers);
    }

    public <T> T getForObject(String url, Object obj, TypeReference<T> clazz, ResultMatcher... matchers) throws Exception{
        return response(get(url), null, multiValueMap(obj), clazz, matchers);
    }

    public <T> T postForObject(String url, Object obj, TypeReference<T> clazz, ResultMatcher... matchers) throws Exception{
        return response(post(url), objectMapperUtil.writeValueAsString(obj), null, clazz, matchers);
    }

    public <T> T patchForObject(String url, Object obj, TypeReference<T> clazz, ResultMatcher... matchers) throws Exception{
        return response(patch(url), objectMapperUtil.writeValueAsString(obj), null, clazz, matchers);
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
