package com.product.util;


import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IpUtil {

    private static final List<String> IP_HEADER_CANDIDATES =  Arrays.asList(
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR",
            "X-Real-IP",
            "X-RealIP",
            "REMOTE_ADDR"
    );

    public static String getClientIp(HttpServletRequest request) {

        String clientIp = null;

        for (String header : IP_HEADER_CANDIDATES) {

            clientIp = request.getHeader(header);

            if (StringUtils.hasText(clientIp) && !"unknown".equalsIgnoreCase(clientIp)) {
                break;
            }

        }

        if (!StringUtils.hasText(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }

        if("0:0:0:0:0:0:0:1".equals(clientIp) || "127.0.0.1".equals(clientIp)){

            try {
                InetAddress address = InetAddress.getLocalHost();
                return address.getHostAddress();
            }catch(UnknownHostException ignored){
                return clientIp;
            }

        }

        return clientIp;

    }

}
