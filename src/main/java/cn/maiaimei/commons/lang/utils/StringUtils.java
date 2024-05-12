package cn.maiaimei.commons.lang.utils;

import java.util.Objects;
import org.springframework.lang.Nullable;

/**
 * Miscellaneous {@link String} utility methods.
 * <p>
 * refer to
 * <p>
 * {@link org.springframework.util.StringUtils}
 * <p>
 * {@link org.apache.commons.lang3.StringUtils}
 */
public final class StringUtils {

  /**
   * Private constructors ensure that classes cannot be instantiated
   */
  private StringUtils() {
    throw new UnsupportedOperationException();
  }

  /**
   * Joins {@code values} using {@code delimiter}
   *
   * @param delimiter the separator character to use
   * @param values    the values to join together, may be null
   * @return the joined String, {@code null} if null values
   */
  public static String concat(String delimiter, String... values) {
    if (hasLength(delimiter) && Objects.nonNull(values)) {
      StringBuilder builder = new StringBuilder();
      final int length = values.length;
      for (int i = 0; i < length; i++) {
        String value = values[i];
        if (hasText(value)) {
          builder.append(value);
          if (i != length - 1) {
            builder.append(delimiter);
          }
        }
      }
      return builder.toString();
    }
    return null;
  }

  /**
   * Check that the given {@code CharSequence} is neither {@code null} nor of length 0.
   *
   * @param str the {@code CharSequence} to check (may be {@code null})
   * @return {@code true} if the {@code CharSequence} is not {@code null} and has length
   */
  public static boolean hasLength(@Nullable CharSequence str) {
    return org.springframework.util.StringUtils.hasLength(str);
  }

  /**
   * Check whether the given {@code String} contains actual <em>text</em>.
   *
   * @param str the {@code String} to check (may be {@code null})
   * @return {@code true} if the {@code String} is not {@code null}, its
   */
  public static boolean hasText(@Nullable String str) {
    return org.springframework.util.StringUtils.hasText(str);
  }

  /**
   * Trim all occurrences of the supplied leading character from the given {@code String}.
   *
   * @param str              the {@code String} to check
   * @param leadingCharacter the leading character to be trimmed
   * @return the trimmed {@code String}
   */
  public static String trimLeadingCharacter(String str, char leadingCharacter) {
    return org.springframework.util.StringUtils.trimLeadingCharacter(str, leadingCharacter);
  }

  /**
   * Trim all occurrences of the supplied trailing character from the given {@code String}.
   *
   * @param str               the {@code String} to check
   * @param trailingCharacter the trailing character to be trimmed
   * @return the trimmed {@code String}
   */
  public static String trimTrailingCharacter(String str, char trailingCharacter) {
    return org.springframework.util.StringUtils.trimTrailingCharacter(str, trailingCharacter);
  }

  /**
   * Trim leading whitespace from the given {@code String}.
   *
   * @param str the {@code String} to check
   * @return the trimmed {@code String}
   * @see java.lang.Character#isWhitespace
   */
  public static String trimLeadingWhitespace(String str) {
    return org.springframework.util.StringUtils.trimLeadingWhitespace(str);
  }

  /**
   * Trim trailing whitespace from the given {@code String}.
   *
   * @param str the {@code String} to check
   * @return the trimmed {@code String}
   * @see java.lang.Character#isWhitespace
   */
  public static String trimTrailingWhitespace(String str) {
    return org.springframework.util.StringUtils.trimTrailingWhitespace(str);
  }

  /**
   * Normalize the path by suppressing sequences like "path/.." and inner simple dots.
   *
   * @param path the original path
   * @return the normalized path
   */
  public static String cleanPath(String path) {
    return org.springframework.util.StringUtils.cleanPath(path);
  }

}
