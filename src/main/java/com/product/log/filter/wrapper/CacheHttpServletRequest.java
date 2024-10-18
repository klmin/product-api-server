package com.product.log.filter.wrapper;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class CacheHttpServletRequest extends HttpServletRequestWrapper {

    private static final String JSON_CONTENT_TYPE = "application/json";
    private static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final Set<String> SUPPORTED_METHODS = Set.of("POST", "PUT", "PATCH");
    private final String body;


    public CacheHttpServletRequest(HttpServletRequest request) throws IOException {

        super(request);

        String contentType = request.getContentType();
        String method = request.getMethod();

        if(contentType == null){
            this.body = null;
        }else{

            contentType = contentType.toLowerCase();

            if(contentType.startsWith(JSON_CONTENT_TYPE)){
                this.body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            }else if(FORM_CONTENT_TYPE.equalsIgnoreCase(contentType) && SUPPORTED_METHODS.contains(method.toUpperCase())){
                this.body = getFormRequestBody(request.getParameterMap());
            }else{
                this.body = null;
            }
        }

    }


    private String getFormRequestBody(Map<String, String[]> parameterMap){

        StringBuilder requestBodyBuilder = new StringBuilder();

        parameterMap.forEach((key, values) -> requestBodyBuilder.append(key).append("=").append(String.join(",", values)).append("&"));

        return formRequestStringCheck(requestBodyBuilder);
    }

    private String formRequestStringCheck(StringBuilder requestBodyBuilder){
       return requestBodyBuilder.isEmpty() ? "" : requestBodyBuilder.substring(0, requestBodyBuilder.length() - 1);
    }

    @Override
    public ServletInputStream getInputStream() {

        if (this.body == null) {
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return true;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener listener) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public int read() {
                    return -1; // EOF (End Of File)
                }
            };
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(this.body.getBytes(StandardCharsets.UTF_8));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return inputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int read() {
                return inputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        if (this.body == null) {
            return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(new byte[0])));
        }
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

}
