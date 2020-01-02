package com.sentinel.engis.config;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "spring.redis.sentinel")
@Getter
@Setter
@NoArgsConstructor
public class RedisSentinelConfigProperties {
	String master;
	Set<String> nodes;
	String password;
}
