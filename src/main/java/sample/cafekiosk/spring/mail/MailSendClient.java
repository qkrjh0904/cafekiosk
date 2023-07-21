package sample.cafekiosk.spring.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MailSendClient {
    public boolean sendEmail(String fromEmail, String toEmail, String title, String contents) {
        // 메일 전송
        log.info("메일 전송");
        throw new IllegalArgumentException("메일 발송에 실패했습니다.");
    }

    public void a() {
        log.info("a");
    }

    public void b() {
        log.info("b");
    }

    public void c() {
        log.info("c");
    }
}
