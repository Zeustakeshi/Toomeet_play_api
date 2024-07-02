package com.toomeet.toomeet_play_api.service.util;

public interface MailService {
    void sendNewAccountEmail(String name, String email, String code);
}
