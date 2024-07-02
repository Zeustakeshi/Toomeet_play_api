package com.toomeet.toomeet_play_api.service;

public interface NanoIdService {
    String generateCustomNanoId();

    String generateNanoId();

    String generateCustomNanoId(int length);

}
