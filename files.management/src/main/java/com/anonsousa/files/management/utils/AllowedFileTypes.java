package com.anonsousa.files.management.utils;

import java.util.Set;

public class AllowedFileTypes {

    public static final Set<String> WHITELIST = Set.of(
            "application/pdf",
            "image/png",
            "image/jpeg"
    );

    private AllowedFileTypes() {}

    public static boolean isAllowed(String contentType) {
        return WHITELIST.contains(contentType);
    }
}
