package com.toomeet.toomeet_play_api.event.listener;

import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.event.EmailVerifyAccountEvent;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.service.util.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
@RequiredArgsConstructor
public class EmailEventListener {

    private final MailService mailService;
    private final Jedis jedis;

    @Async
    @EventListener
    public void handleSendMailVerifyAccountEvent(EmailVerifyAccountEvent event) {
        try {
            mailService.sendNewAccountEmail(event.getName(), event.getEmail(), event.getCode());
        } catch (Exception e) {
            jedis.del(event.getCode());
            throw new ApiException(ErrorCode.SEND_EMAIL_VERIFY_NEW_ACCOUNT_FAIL);
        }
    }
}
