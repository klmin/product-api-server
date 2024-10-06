package com.product.config.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = CustomSecurityContextFactory.class)
public @interface WithMockUserCustom {

    long userId() default 4L;
    String loginId() default "admin";
    String username() default "관리자";
    String password() default "1234@aA!";
    String[] roles() default {"ROLE_ADMIN"};
}
