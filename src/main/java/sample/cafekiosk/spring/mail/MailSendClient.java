package sample.cafekiosk.spring.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MailSendClient {
    public boolean sendEmail(String fromEmail, String toEmail, String title, String contents) {
        // 메일 전송
        log.info("메일 전송");
        return true;
    }
}
