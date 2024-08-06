package com.toomeet.toomeet_play_api.service.util.impl;

import com.toomeet.toomeet_play_api.service.util.MailService;
import com.toomeet.toomeet_play_api.utils.EmailUtils;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    public static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
    private final JavaMailSender sender;

    @Value("${frontend.home_url}")
    private String frontEndHomeUrl;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    @SneakyThrows
    public void sendNewAccountEmail(String name, String email, String code) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        helper.setText(EmailUtils.getEmailNewAccountMessage(name, frontEndHomeUrl, code), true);
        helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
        helper.setTo(email);
        helper.setFrom(fromEmail);
        sender.send(message);
    }
}
