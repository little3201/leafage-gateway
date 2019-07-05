/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author liwenqiang
 */
@SpringBootApplication
public class AbeilleGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbeilleGatewayApplication.class, args);
    }

}

