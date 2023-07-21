package sample.cafekiosk.spring.api.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.mail.repository.MailSendHistoryRepository;
import sample.cafekiosk.spring.domain.entity.MailSendHistory;
import sample.cafekiosk.spring.mail.MailSendClient;

@Service
@Transactional
@RequiredArgsConstructor
public class MailService {

    private final MailSendClient mailSendClient;
    private final MailSendHistoryRepository mailSendHistoryRepository;

    public boolean sendMail(String fromEmail, String toEmail, String title, String contents) {
        boolean result = mailSendClient.sendEmail(fromEmail, toEmail, title, contents);
        if (result) {
            MailSendHistory mailSendHistory = MailSendHistory.builder()
                    .fromEmail(fromEmail)
                    .toEmail(toEmail)
                    .title(title)
                    .contents(contents)
                    .build();
            mailSendHistoryRepository.save(mailSendHistory);

            mailSendClient.a();
            mailSendClient.b();
            mailSendClient.c();

            return true;
        }
        return false;
    }
}
