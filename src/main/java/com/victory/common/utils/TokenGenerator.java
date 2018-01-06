package com.victory.common.utils;

import com.victory.common.domain.result.ExceptionMsg;
import com.victory.common.exception.BaseException;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * Created by ajkx
 * Date: 2017/8/30.
 * Time:14:05
 */
public class TokenGenerator {

    public static String generateValue() {
        return generateValue(UUID.randomUUID().toString());
    }

    private static final char[] hexCode = "0123456789abcdef".toCharArray();

    public static String toHexString(byte[] data) {
        if(data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length*2);
        for ( byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }
    
    // TODO: 2017/8/30  异常抛出机制可优化
    public static String generateValue(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            throw new BaseException(ExceptionMsg.TokenGenerateFailed);
        }
    }
}
