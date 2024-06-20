package com.toomeet.toomeet_play_api.utils;

public class EmailUtils {

    public static String getEmailNewAccountMessage(String name, String host, String token) {
        return String.format("""
                    <h2>Hello %s,</h2>
                    <p>Your new account has been created. Please click on the link below to verify you account</p>
                    <a href = "%s"> verify now! </a>
                    <p>Toomeet Support Teams.</p>
                """, name, getNewAccountVerificationUrl(host, token));
    }

    public static String getNewAccountVerificationUrl(String host, String token) {
        return host + "/auth/verify-account?code=" + token;
    }

}
