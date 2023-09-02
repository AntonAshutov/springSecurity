package com.example.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Objects;

@Component
@Slf4j
public class KeyUtils {

    private KeyPair _accessTokenKeyPair;

    private KeyPair getAccessTokenKeyPair() {
        if (Objects.isNull(_accessTokenKeyPair)) {
            KeyPairGenerator keyPairGenerator;
            try {
                keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            // Устанавливаем размер ключей (в битах), например, 2048 бит
            keyPairGenerator.initialize(2048);

            _accessTokenKeyPair = keyPairGenerator.generateKeyPair();
        }
        return _accessTokenKeyPair;
    }


    public RSAPublicKey getAccessTokenPublicKey() {
        return (RSAPublicKey) getAccessTokenKeyPair().getPublic();
    }

    public RSAPrivateKey getAccessTokenPrivateKey() {
        return (RSAPrivateKey) getAccessTokenKeyPair().getPrivate();
    }

//    private KeyPair getKeyPair(String publicKeyPath, String privateKeyPath) {
//        KeyPair keyPair;
//
//        File publicKeyFile = new File(publicKeyPath);
//        File privateKeyFile = new File(privateKeyPath);
//
//        if (publicKeyFile.exists() && privateKeyFile.exists()) {
//            log.info("loading keys from file: {}, {}", publicKeyPath, privateKeyPath);
//            try {
//                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//
//                byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
//                EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
//                PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
//
//                byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
//                PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
//                PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
//
//                keyPair = new KeyPair(publicKey, privateKey);
//                return keyPair;
//            } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException e) {
//                throw new RuntimeException(e);
//            }
//        } else {
//            if (Arrays.stream(environment.getActiveProfiles()).anyMatch(s -> s.equals("prod"))) {
//                throw new RuntimeException("public and private keys don't exist");
//            }
//        }
//
//        File directory = new File("access-refresh-token-keys");
//        if (!directory.exists()) {
//            directory.mkdirs();
//        }
//        try {
//            log.info("Generating new public and private keys: {}, {}", publicKeyPath, privateKeyPath);
//            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//            keyPairGenerator.initialize(2048);
//            keyPair = keyPairGenerator.generateKeyPair();
//            try (FileOutputStream fos = new FileOutputStream(publicKeyPath)) {
//                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyPair.getPublic().getEncoded());
//                fos.write(keySpec.getEncoded());
//            }
//
//            try (FileOutputStream fos = new FileOutputStream(privateKeyPath)) {
//                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());
//                fos.write(keySpec.getEncoded());
//            }
//        } catch (NoSuchAlgorithmException | IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        return keyPair;
//    }

}
