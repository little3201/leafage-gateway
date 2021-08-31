package io.leafage.gateway.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import javax.mail.internet.MimeMessage;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * 邮件发送测试类
 *
 * @author liwenqiang 2021/8/30 17:04
 */
@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    void send() {
        given(this.javaMailSender.createMimeMessage()).willReturn(Mockito.mock(MimeMessage.class));

        emailService.send("liwenqiang1687@ichinae.com", "测试发送", "哈喽\n 我是测试的消息\n");
        verify(javaMailSender, times(1)).send(Mockito.any(MimeMessage.class));
    }

    @Test
    void send_error() {
        given(this.javaMailSender.createMimeMessage()).willThrow(new MailSendException("发送失败。"));

        Assertions.assertThrows(MailSendException.class, () ->
                emailService.send("liwenqiang1687@ichinae.com", "测试发送", "哈喽\n 我是测试的消息\n"));
    }
}