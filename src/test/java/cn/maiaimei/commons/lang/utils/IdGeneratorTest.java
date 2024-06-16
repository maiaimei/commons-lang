package cn.maiaimei.commons.lang.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import cn.maiaimei.commons.lang.constants.DateTimeConstants;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class IdGeneratorTest {

  @Test
  public void testNextId() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateTimeConstants.YYYYMMDD);

    final BigDecimal id = IdGenerator.nextId();
    log.info("{}", id);
    assertTrue(id.toPlainString().startsWith(formatter.format(LocalDateTime.now())));

    final BigDecimal idWithCapacity = IdGenerator.nextId(5);
    log.info("{}", idWithCapacity);
    assertTrue(idWithCapacity.toPlainString().startsWith(formatter.format(LocalDateTime.now())));
  }

  @Test
  public void testNextIdString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateTimeConstants.YYYYMMDD);

    final String id = IdGenerator.nextIdString();
    log.info("{}", id);
    assertTrue(id.startsWith(formatter.format(LocalDateTime.now())));

    final String idWithCapacity = IdGenerator.nextIdString(5);
    log.info("{}", idWithCapacity);
    assertTrue(idWithCapacity.startsWith(formatter.format(LocalDateTime.now())));
  }
}
