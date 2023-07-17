package sample.cafekiosk.spring.api.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.cafekiosk.spring.domain.entity.MailSendHistory;

public interface MailSendHistoryRepository extends JpaRepository<MailSendHistory, Long> {
}
