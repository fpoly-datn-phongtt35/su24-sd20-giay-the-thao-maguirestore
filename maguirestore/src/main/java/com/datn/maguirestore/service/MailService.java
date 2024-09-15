package com.datn.maguirestore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class MailService {

    private static final String USER = "user";
    private static final String BASE_URL = "baseUrl";
    private final JavaMailSender javaMailSender;

    @Async
    public void sendEmail1(String to, String subject, String content, byte[] byteArrayResource, boolean isMultipart, boolean isHtml) {
//        log.debug(
//                "Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
//                isMultipart,
//                isHtml,
//                to,
//                subject,
//                content
//        );

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
//            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(new String(byteArrayResource, StandardCharsets.UTF_8), isHtml);
            //            message.addAttachment("response.docx", byteArrayResource);
            javaMailSender.send(mimeMessage);
//            log.debug("Sent email to User '{}'", to);
        } catch (MailException | MessagingException e) {
//            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }
}
