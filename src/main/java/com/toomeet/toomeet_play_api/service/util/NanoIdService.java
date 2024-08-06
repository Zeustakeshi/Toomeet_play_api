package com.toomeet.toomeet_play_api.service.util;

public interface NanoIdService {
    String generateCustomNanoId();

    String generateNanoId();

    String generateCustomNanoId(int length);
}
