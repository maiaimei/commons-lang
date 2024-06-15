package cn.maiaimei.commons.lang.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import cn.maiaimei.commons.lang.constants.DateTimeConstants;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

public class IdGeneratorTest {

  @Test
  public void testNextId() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateTimeConstants.YYYYMMDDHHMM);
    final BigDecimal id = IdGenerator.nextId();
    assertTrue(id.toPlainString().startsWith(formatter.format(LocalDateTime.now())));
  }

  @Test
  public void testNextIdString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateTimeConstants.YYYYMMDDHHMM);
    final String id = IdGenerator.nextIdString();
    assertTrue(id.startsWith(formatter.format(LocalDateTime.now())));
  }
}
