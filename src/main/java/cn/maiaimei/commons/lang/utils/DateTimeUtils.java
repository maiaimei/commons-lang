package cn.maiaimei.commons.lang.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtils {

  private DateTimeUtils() {
    throw new UnsupportedOperationException();
  }

  public static String formatNow(String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return formatter.format(LocalDateTime.now());
  }

}
