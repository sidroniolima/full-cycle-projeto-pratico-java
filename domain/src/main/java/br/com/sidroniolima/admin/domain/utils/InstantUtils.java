package br.com.sidroniolima.admin.domain.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public final class InstantUtils {

    private InstantUtils() {}

    // 9 casas = NANOSECONDS
    // 6 casas = MICROSECONDS
    public static Instant now() {
        return Instant.now().truncatedTo(ChronoUnit.MICROS);
    }
}
