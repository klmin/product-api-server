package com.product;

import com.product.auth.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductApiServerApplicationTest {

    @Autowired
    private AuthenticationService authenticationService;
   

    @Test
    void main() {

        


    }
}