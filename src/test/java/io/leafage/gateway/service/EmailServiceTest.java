package io.leafage.gateway.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void sendEmail() {
        emailService.sendEmail("liwenqiang1687@ichinae.com", "测试发送", "哈喽\n 我是测试的消息\n");
    }
}