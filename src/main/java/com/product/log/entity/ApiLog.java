package com.product.log.entity;

import com.product.jpa.audit.RegTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name="tb_api_log")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@DynamicInsert
public class ApiLog extends RegTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long apiLogId;

    @Comment("요청IP")
    private String clientIp;

    @Comment("주소")
    @Column(columnDefinition = "TEXT")
    private String url;

    @Comment("컨텐츠타입")
    private String contentType;

    @Comment("HTTP 메서드")
    private String httpMethod;

    @Comment("쿼리스트링")
    @Column(columnDefinition = "TEXT")
    private String queryString;

    @Comment("요청데이터")
    @Column(columnDefinition = "TEXT")
    private String requestBody;

    @Comment("응답데이터")
    @Column(columnDefinition = "TEXT")
    private String responseBody;

    @Builder
    public ApiLog(String clientIp, String url, String contentType, String httpMethod, String queryString, String requestBody, String responseBody){
        this.clientIp = clientIp;
        this.url = url;
        this.contentType = contentType;
        this.httpMethod = httpMethod;
        this.queryString = queryString;
        this.requestBody = requestBody;
        this.responseBody = responseBody;
    }

}
