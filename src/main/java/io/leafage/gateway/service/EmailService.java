package io.leafage.gateway.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * email service .
 *
 * @author liwenqiang 2019-03-03 22:55
 */
@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Resource
    private JavaMailSender javaMailSender;

    public void send(String target, String subject, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("little3201@163.com");
            helper.setTo(target);
            helper.setSubject("[Leafage] " + subject);
            helper.setText(text, true);
            this.javaMailSender.send(message);
        } catch (MailException | MessagingException ex) {
            log.error("Send email to {} error: ", target, ex);
        }
    }

}
