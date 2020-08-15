package com.myp.utils.security;


import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

    //用于验签的公钥
    public static final String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMio/FMUHRO2T1nPuJPAVrodz+CYEdkaSr4BO/KRiel95X1ekAYfb7mI2IZSq6nNETM2AOIU+4SbNuIBNYbesG+Juigf3l1hltPhIxOTkDpBia6mDS1sPMoQ7XL+6rYZWs9itLx4USVhmEyH8T9I3K/056G40fE5y6zTTL8IqH2QIDAQAB";
    //用于验签的私钥
    public static final String privateKeyString = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIyKj8UxQdE7ZPWc+4k8BWuh3P4JgR2RpKvgE78pGJ6X3lfV6QBh9vuYjYhlKrqc0RMzYA4hT7hJs24gE1ht6wb4m6KB/eXWGW0+EjE5OQOkGJrqYNLWw8yhDtcv7qthlaz2K0vHhRJWGYTIfxP0jcr/TnobjR8TnLrNNMvwiofZAgMBAAECgYEAhR+i+4ii7YwzH6wpX17pS4CxEjjygo24y82fJs3IskDsrYrEAEbLapqgRfh+NvHf4S4i6H+JQekd+0yKxpKnsizAwjERBgkMyFkh22g4St0wQVnSlteISdVzBv9trmbq9ZTptImCUhaw9az4x2o7KwTqwDgr/xiUa1VkNvvbNpkCQQDsHg/YtSl06AdixDfwTwmKXZC4It3Hl4OQ1jbeMQO6oGR+tyntqPVtlJfT0w94EyLoSr+HZ6r0s3IQ9WdXzQ4/AkEAmGAut2yEneJjM4VhJ+hwO/qJW9oTND3fQT3te5hVtXwlr8exG70i4jjQfkZFvvxv48oD2wjHhvlu9tMXTIwT5wJBAJHWk5UXeG6z7fPsHFz6hHsJBZ0mM/PWurWYCjW88n+wF8JIocYgSM8RK6HzSCezMilmGdyphGc1+utMmDMb/YMCQFEshBahD/EQJmrO9lgNQqUhLcWp4islfubBt97s41QAZA9FoXs2gfvj6mYqx4Mb9ftypiUsgybLgKqq/7F+uicCQCF0Tiwbbf5WIf9KxNgGDAG61ScPY9Tt7zCThgiHj5izYCtyEeAMhQzhe3BM14W7RIKIQSR3Z3Oa+/1c7QoSn4g=";
    //用于解密的公钥
    public static final String publicDecodeKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJ24IhJQ54nOYQjl49j9lmwUaJJs9RMoyOwfcEmyXrzKE50XyT3IUxYmfB65Zo4PTHb5OndJQnoJfabvHZVeNKj+9Tmi2BXMnQh3BEN2a6HRXBnkySUbLMf9stHrcoOvDsJrZ0PLA1oIZHEoLyKZD/NFqwA0Xng+Rjtf/o14FvIQIDAQAB";
    //用于解密的私钥
    public static final String privateDecodeKeyString = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAInbgiElDnic5hCOXj2P2WbBRokmz1EyjI7B9wSbJevMoTnRfJPchTFiZ8Hrlmjg9Mdvk6d0lCegl9pu8dlV40qP71OaLYFcydCHcEQ3ZrodFcGeTJJRssx/2y0etyg68OwmtnQ8sDWghkcSgvIpkP80WrADReeD5GO1/+jXgW8hAgMBAAECgYBCkMCT+o2zRad9ZREyTqxeBoNlpFzEy1C9egEpszSrWEKdZX7u8rNJtkd9hqE5AS6QwlqcqBkFzXClo56aH/PAjIF/2dAhAhrdvNABrxB2h/PdUkTL5XCck1TNy04jzUgxULW/7BScQ0K68A7LNu7282ZzhIG0tYF0aCBObsLE8QJBANuC/iQIoT4aOrhMDwcHeRajgQrB7TekAw1BmOoXOGqzVOHl08b6Gv/NaYXM9QUwK84thpobjApl9+RTZ83jSm0CQQCgxdX9JVibTSRxKjj3XtxiqHnA6n+9zmiZAcgsV2Uo7bMnqsUPJ0CkgAZ4JA5DIDrni1wDM1O9NCRPH7SiKAcFAkBhaVkUbov3fjZOsNn+WY+fv0E1n+eASJVeHZ0ZTOKpXxmtAYuggj7XA7XvPYwCGGVoIoXX/59+wc9nEKhBErtlAkBbJk7gKuBFjELw9eM+PEXumV4OBeVOk0uyE9SNby8nOTytbKA0qyh3Gy6PxsFfRVKgG96a4erEBl/fjDY5CUCRAkEAkZh2Gl1QEnEO2SR/hNnKI60KpGWzt0JNva2EvUZV8eChK8LUqLktggM3M6BOV0jSxpP6YKM+X3eZeFpgvUO4iA==";

    public static void main(String[] args) {

        String a = "";
        Integer b = Integer.parseInt(a);
        String tempUserString = Integer.toString(b);
        
        System.out.println(b);
    }

    //解密
    public static String decrypt(String dataString) {
        try {
            PrivateKey privateKey = RSAUtil.loadPrivateKey(privateDecodeKeyString);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE,privateKey);

            byte[] bytes = Base64.getDecoder().decode(dataString);
            byte[] b = cipher.doFinal(bytes);
            return new String(b);

        }catch (Exception e){
            e.printStackTrace();
        }

        return "";

    }

    //验签
    public static boolean verifyIdentify(String textString,String signStr){
        PublicKey key = RSAUtil.loadPublicKey(publicKeyString);
        try {
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initVerify(key);
            signature.update(textString.getBytes());

            boolean success = signature.verify(Base64.getDecoder().decode(signStr));
            System.out.println("验证成功？--"+(success?"YES":"NO"));
            return success;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    //加载公钥
    public static RSAPublicKey loadPublicKey(String publicKeyString){
        try {
            byte[] buffer = Base64.getDecoder().decode(publicKeyString);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey)keyFactory.generatePublic(keySpec);
        }catch (Exception e){

        }

        return null;
    }


    //加载私钥
    public static RSAPrivateKey loadPrivateKey(String priKeyString){
        try {
            PKCS8EncodedKeySpec priPK = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(priKeyString));
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = factory.generatePrivate(priPK);

            return (RSAPrivateKey) privateKey;
        }catch (Exception e){

        }
        return null;
    }



}
