package br.com.rbsj.seplag.infrastructure.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "br.com.rbsj.seplag.infrastructure")
public class FeignConfig {
}
