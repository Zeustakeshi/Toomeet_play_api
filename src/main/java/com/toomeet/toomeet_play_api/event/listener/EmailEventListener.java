package com.toomeet.toomeet_play_api.event.listener;


import com.toomeet.toomeet_play_api.event.EmailVerifyAccountEvent;
import com.toomeet.toomeet_play_api.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailEventListener {

    private final MailService mailService;

    @EventListener
    public void handleSendMailEvent(EmailVerifyAccountEvent event) {
        mailService.sendNewAccountEmail(event.getName(), event.getEmail(), event.getCode());
    }
}
