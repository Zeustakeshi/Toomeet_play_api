package com.toomeet.toomeet_play_api.service.util.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.toomeet.toomeet_play_api.service.util.NanoIdService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class NanoIdServiceImpl implements NanoIdService {
    private static final SecureRandom random = new SecureRandom();
    private static final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    @Override
    public String generateCustomNanoId() {
        return generateCustomNanoId(10);
    }

    @Override
    public String generateNanoId() {
        return NanoIdUtils.randomNanoId();
    }

    @Override
    public String generateCustomNanoId(int length) {
        return NanoIdUtils.randomNanoId(random, alphabet, length);
    }


}
