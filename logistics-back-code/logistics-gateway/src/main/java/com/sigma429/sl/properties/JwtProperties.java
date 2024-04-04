package com.sigma429.sl.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sl.jwt")
@Data
public class JwtProperties {

    private String publicKey;
}
