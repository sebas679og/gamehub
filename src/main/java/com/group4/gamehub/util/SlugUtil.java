package com.group4.gamehub.util;

import java.util.Locale;
import java.util.function.Function;

/**
 * Utility class for generating URL-friendly slugs from input strings. It also provides
 * functionality to generate unique slugs based on a name and a custom existence check.
 */
public class SlugUtil {

  /**
   * Converts a given string into a URL-friendly slug. The process includes:
   *
   * <ul>
   *   <li>Lowercasing the input
   *   <li>Removing all non-alphanumeric characters (excluding spaces)
   *   <li>Replacing spaces with hyphens
   *   <li>Collapsing multiple hyphens into a single one
   *   <li>Trimming leading and trailing hyphens
   * </ul>
   *
   * @param input the input string to convert
   * @return the generated slug
   */
  public static String toSlug(String input) {
    return input
        .toLowerCase(Locale.ROOT)
        .replaceAll("[^a-z0-9\\s]", "")
        .replaceAll("\\s+", "-")
        .replaceAll("-{2,}", "-")
        .replaceAll("^-|-$", "");
  }

  /**
   * Generates a unique slug for a given name. It starts with a base slug and appends a numeric
   * suffix if needed, ensuring uniqueness using the provided predicate function.
   *
   * @param name the name to convert into a unique slug
   * @param existsFn a function that checks whether a slug already exists (returns true if it
   *     exists)
   * @return a unique slug that does not conflict with existing ones
   */
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
