package sample.cafekiosk.spring.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MailSendHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromEmail;
    private String toEmail;
    private String title;
    private String contents;

    @Builder
    private MailSendHistory(String fromEmail, String toEmail, String title, String contents) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.title = title;
        this.contents = contents;
    }
}
