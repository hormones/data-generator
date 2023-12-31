package com.hormones.random.util;

import com.github.javafaker.Faker;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class FieldUtil {

    public static final Faker FAKER_CN = new Faker(Locale.SIMPLIFIED_CHINESE);
    public static final Faker FAKER_EN = new Faker(Locale.ENGLISH);

    public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String calculateMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            // 转换为十六进制字符串
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder sb = new StringBuilder();
            sb.append(no.toString(16));

            // 确保十六进制字符串长度为32位
            while (sb.length() < 32) {
                sb.insert(0, "0");
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getPath(String filePath) {
        if (filePath.startsWith("/")) {
            filePath = filePath.substring(1);
        }
        return Objects.requireNonNull(FieldUtil.class.getResource("/")).getPath() + filePath;
    }
}
