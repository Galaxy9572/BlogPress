package com.blogpress.common.util;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * 哈希工具类
 * @author JY
 */
@Slf4j
public class HashUtils {

    /**
     * 利用java原生的类实现SHA256加密
     * @param str 参数拼接的字符串
     * @return sha256哈希
     */
    public static String sha256(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            log.error("", e);
        }
        return encodeStr;
    }

    /**
     * 随机生成UUID
     * @return UUID
     */
    public static String randomUuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 将byte转为16进制
     * @param bytes byte数组
     * @return 16进制字符串
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            String temp = Integer.toHexString(b & 0xFF);
            if (temp.length() == 1) {
                // 1得到一位的进行补0操作
                builder.append("0");
            }
            builder.append(temp);
        }
        return builder.toString();
    }

}
