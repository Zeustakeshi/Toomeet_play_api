package com.toomeet.toomeet_play_api.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeyUtils {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Value("${jwt.key.access_token.public_key}")
    private String accessTokenPublicKeyPath;

    @Value("${jwt.key.access_token.private_key}")
    private String accessTokenPrivateKeyPath;

    @Value("${jwt.key.refresh_token.public_key}")
    private String refreshTokenPublicKeyPath;

    @Value("${jwt.key.refresh_token.private_key}")
    private String refreshTokenPrivateKeyPath;

    public static RSAPublicKey loadPublicKey(String filePath)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] keyBytes = fis.readAllBytes();
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(spec);
        }
    }

    public static RSAPrivateKey loadPrivateKey(String filePath)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] keyBytes = fis.readAllBytes();
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(spec);
        }
    }

    public RSAPublicKey getAccessTokenPublicKey() {
        try {
            return loadPublicKey(accessTokenPublicKeyPath);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public PrivateKey getAccessTokenPrivateKey() {
        try {
            return loadPrivateKey(accessTokenPrivateKeyPath);
        } catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
