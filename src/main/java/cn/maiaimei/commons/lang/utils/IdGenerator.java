package cn.maiaimei.commons.lang.utils;

import cn.maiaimei.commons.lang.constants.DateTimeConstants;
import cn.maiaimei.commons.lang.constants.NumberConstants;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public final class IdGenerator {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(
      DateTimeConstants.YYYYMMDDHHMMSSSSS);

  private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(NumberConstants.ZERO);

  private IdGenerator() {
    throw new UnsupportedOperationException();
  }

  public static BigDecimal nextId() {
    return new BigDecimal(nextIdString());
  }

  public static String nextIdString() {
    final String serialNumber = String.format("%03d", ATOMIC_INTEGER.getAndIncrement());
    return String.format("%s%s", FORMATTER.format(LocalDateTime.now()), serialNumber);
  }

}
