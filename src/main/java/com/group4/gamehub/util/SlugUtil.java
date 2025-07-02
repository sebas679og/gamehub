package com.group4.gamehub.util;

import java.util.function.Function;

public class SlugUtil {
    public static  String toSlug(String input){
        return input
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-{2,}", "-")
                .replaceAll("^-|-$", "");
    }

    public static String toUniqueSlug(String name, Function<String, Boolean> existsFn) {
        String baseSlug = toSlug(name);
        String slug = baseSlug;
        int counter = 1;
        while (existsFn.apply(slug)) {
            slug = baseSlug + "-" + counter++;
        }
        return slug;
    }
}
