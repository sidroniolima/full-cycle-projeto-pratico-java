package br.com.sidroniolima.admin.infrastructure.utils;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public final class HashingUtils {
    private static final HashFunction CHEKSUM = Hashing.crc32c();

    private HashingUtils() {}

    public static String checksum(final byte[] content) {
        return CHEKSUM.hashBytes(content).toString();
    }

}
