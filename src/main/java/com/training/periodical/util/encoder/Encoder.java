package com.training.periodical.util.encoder;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class helps to encrypt user password for safe keeping.
 */
public class Encoder {
    private static final Logger log = Logger.getLogger(Encoder.class);

    public static String encrypt(String password) throws EncoderException {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.error("exception in encrypt method of " + Encoder.class.getSimpleName());
            throw new EncoderException("exception in encrypt method of " + Encoder.class.getSimpleName());
        }

        if (md5 == null) throw new EncoderException("exception in encrypt method of " + Encoder.class.getSimpleName());
        byte[] digest = md5.digest(password.getBytes());

        StringBuilder sb = new StringBuilder(digest.length * 2);
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
