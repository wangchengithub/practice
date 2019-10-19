package com.practice.web;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

public class RC4 {
    private final byte[] S = new byte[256];
    private final byte[] T = new byte[256];
    private final int keylen;

    public RC4(final byte[] key) {
        if (key.length < 1 || key.length > 256) {
            throw new IllegalArgumentException(
                    "key must be between 1 and 256 bytes");
        } else {
            keylen = key.length;
            for (int i = 0; i < 256; i++) {
                S[i] = (byte) i;
                T[i] = key[i % keylen];
            }
            int j = 0;
            for (int i = 0; i < 256; i++) {
                j = (j + S[i] + T[i]) & 0xFF;
                S[i] ^= S[j];
                S[j] ^= S[i];
                S[i] ^= S[j];
            }
        }
    }

    public byte[] encrypt(final byte[] plaintext) {
        final byte[] ciphertext = new byte[plaintext.length];
        int i = 0, j = 0, k, t;
        for (int counter = 0; counter < plaintext.length; counter++) {
            i = (i + 1) & 0xFF;
            j = (j + S[i]) & 0xFF;
            S[i] ^= S[j];
            S[j] ^= S[i];
            S[i] ^= S[j];
            t = (S[i] + S[j]) & 0xFF;
            k = S[t];
            ciphertext[counter] = (byte) (plaintext[counter] ^ k);
        }
        return ciphertext;
    }

    public byte[] decrypt(final byte[] ciphertext) {
        return encrypt(ciphertext);
    }

    public static void main(String[] args) throws Exception {
        RC4 rc4 = new RC4("abc".getBytes());
        System.out.println(new String(rc4.encrypt("1".getBytes())));
        System.out.println(new String(rc4.encrypt("2".getBytes())));
        System.out.println(new String(rc4.encrypt((Integer.MAX_VALUE + "").getBytes())));
        //AAABbJXNmuE=
        System.out.println(new String(Base64.getEncoder().encode(longToByteArray(System.currentTimeMillis()))));
        System.out.println(shortUUID());
    }

    public static byte[] longToByteArray(long num) {

        byte[] result = new byte[8];

        result[0] = (byte) (num >>> 56);// 取最高8位放到0下标

        result[1] = (byte) (num >>> 48);// 取最高8位放到0下标

        result[2] = (byte) (num >>> 40);// 取最高8位放到0下标

        result[3] = (byte) (num >>> 32);// 取最高8位放到0下标

        result[4] = (byte) (num >>> 24);// 取最高8位放到0下标

        result[5] = (byte) (num >>> 16);// 取次高8为放到1下标

        result[6] = (byte) (num >>> 8); // 取次低8位放到2下标

        result[7] = (byte) (num); // 取最低8位放到3下标

        return result;

    }

    public static String shortUUID() {
        UUID uuid = UUID.randomUUID();
        return shortUUID(uuid);
    }

    protected static String shortUUID(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());

        return Base64.getEncoder().withoutPadding().encodeToString(byteBuffer.array())
                .replaceAll("/", "_")
                .replaceAll("\\+", "-");

    }
}