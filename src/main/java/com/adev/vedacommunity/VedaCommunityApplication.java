package com.adev.vedacommunity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VedaCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(VedaCommunityApplication.class, args);
    }

}
