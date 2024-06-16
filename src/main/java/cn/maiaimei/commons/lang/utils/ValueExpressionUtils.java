package cn.maiaimei.commons.lang.utils;

import cn.maiaimei.commons.lang.constants.NumberConstants;
import cn.maiaimei.commons.lang.constants.StringConstants;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.Assert;

/**
 * Miscellaneous value expression utility methods.
 */
public final class ValueExpressionUtils {

  /**
   * A string that satisfies the ${key} condition, where key is an alphabet, number, etc
   */
  private static final Pattern KEY_PATTERN = Pattern.compile("\\$\\{[^}]*\\}");

  /**
   * The key prefix: ${
   */
  private static final Pattern KEY_PATTERN_PREFIX = Pattern.compile("\\$\\{");

  /**
   * The key subfix: }
   */
  private static final Pattern KEY_PATTERN_SUBFIX = Pattern.compile("\\}");

  /**
   * The key format
   */
  private static final String KEY_FORMAT = "${%s}";

  /**
   * The key: currentTimestamp
   */
  private static final String KEY_CURRENTTIMESTAMP = "currentTimestamp";

  /**
   * The key: serialNumber
   */
  private static final String KEY_SERIAL_NUMBER = "serialNumber";

  /**
   * AtomicInteger
   */
  private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(NumberConstants.ZERO);

  /**
   * Private constructors ensure that classes cannot be instantiated
   */
  private ValueExpressionUtils() {
    throw new UnsupportedOperationException();
  }

  /**
   * Replace placeholders with real values
   *
   * @param expressionString the expression string to use
   * @return a new string without placeholder
   */
  public static String parse(String expressionString) {
    return parse(expressionString, ATOMIC_INTEGER);
  }

  /**
   * Replace placeholders with real values
   *
   * @param expressionString the expression string to use
   * @param atomicInteger    the atomicInteger to use
   * @return a new string without placeholder
   */
  public static String parse(String expressionString, AtomicInteger atomicInteger) {
    final List<String> keys = findKeys(expressionString);
    final Map<String, String> params = resolveKeys(keys, atomicInteger);
    for (Entry<String, String> entry : params.entrySet()) {
      expressionString = expressionString.replace(entry.getKey(), entry.getValue());
    }
    return expressionString;
  }

  /**
   * Replace placeholders with real values
   *
   * @param expressionString the expression string to use
   * @param params           the params to use
   * @return a new string without placeholder
   */
  public static String parse(String expressionString, Map<String, String> params) {
    return parse(expressionString, params, ATOMIC_INTEGER);
  }

  /**
   * Replace placeholders with real values
   *
   * @param expressionString the expression string to use
   * @param params           the params to use
   * @param atomicInteger    the atomicInteger to use
   * @return a new string without placeholder
   */
  public static String parse(String expressionString, Map<String, String> params,
      AtomicInteger atomicInteger) {
    final List<String> keys = findKeys(expressionString);
    final Map<String, String> allParams = resolveKeys(keys, atomicInteger);
    params.forEach((key, value) -> allParams.put(String.format(KEY_FORMAT, key), value));
    for (Entry<String, String> entry : allParams.entrySet()) {
      expressionString = expressionString.replace(entry.getKey(), entry.getValue());
    }
    return expressionString;
  }

  /**
   * Find all strings that satisfies the ${key} condition, where key is an alphabet, number, etc
   *
   * @param expressionString the expression string to use
   * @return a list of key
   */
  private static List<String> findKeys(String expressionString) {
    Matcher matcher = KEY_PATTERN.matcher(expressionString);
    List<String> keys = new ArrayList<>();
    while (matcher.find()) {
      final String key = matcher.group();
      if (StringUtils.hasText(key)) {
        keys.add(key);
      }
    }
    return keys;
  }

  /**
   * extract key
   *
   * @param key the key to use
   * @return a key without ${ and }
   */
  private static String extractKey(String key) {
    return KEY_PATTERN_SUBFIX.matcher(
            KEY_PATTERN_PREFIX.matcher(key).replaceAll(StringConstants.EMPTY))
        .replaceAll(StringConstants.EMPTY);
  }

  /**
   * resolve keys
   *
   * @param keys          the list of key to use
   * @param atomicInteger the atomicInteger to use
   * @return a key value pairs
   */
  private static Map<String, String> resolveKeys(List<String> keys, AtomicInteger atomicInteger) {
    final Map<String, String> params = Maps.newHashMap();
    for (String key : keys) {
      final String shortKey = extractKey(key);
      if (shortKey.startsWith(KEY_CURRENTTIMESTAMP)) {
        params.put(key, resolveDateTime(shortKey));
      } else if (shortKey.startsWith(KEY_SERIAL_NUMBER)) {
        params.put(key, resolveSerialNumber(shortKey, atomicInteger));
      }
    }
    return params;
  }

  /**
   * resolve date and time
   *
   * @param shortKey the shortKey to use
   * @return the actual value of date and time
   */
  private static String resolveDateTime(String shortKey) {
    final String[] split = StringUtils.split(shortKey, StringConstants.MAPPING);
    Assert.notNull(split, "Invalid currentTimestamp key");
    final String pattern = split[NumberConstants.ONE];
    return DateTimeUtils.formatNow(pattern);
  }

  /**
   * resolve serial number
   *
   * @param shortKey      the shortKey to use
   * @param atomicInteger the atomicInteger to use
   * @return the actual value of serial number
   */
  private static String resolveSerialNumber(String shortKey, AtomicInteger atomicInteger) {
    final String[] split = StringUtils.split(shortKey, StringConstants.MAPPING);
    Assert.notNull(split, "Invalid serialNumber key");
    final String format = split[NumberConstants.ONE];
    return String.format(format, atomicInteger.getAndIncrement());
  }

}
