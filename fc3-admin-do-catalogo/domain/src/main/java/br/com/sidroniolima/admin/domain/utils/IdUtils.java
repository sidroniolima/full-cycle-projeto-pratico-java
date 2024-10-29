package br.com.sidroniolima.admin.domain.utils;

import br.com.sidroniolima.admin.domain.category.CategoryID;

import java.util.UUID;

public final class IdUtils {
    private IdUtils() {

    }

    public static String uuid() {
        return UUID.randomUUID().toString().toLowerCase().replace("-","");
    }
}
