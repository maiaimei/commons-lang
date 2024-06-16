package cn.maiaimei.commons.lang.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ValueExpressionUtilsTest {

  @Test
  public void testParse() {
    String exp = "test-in_${currentTimestamp->yyyyMMddHHmmssSSS}${serialNumber->%05d}.txt";
    for (int i = 0; i < 100; i++) {
      log.info("{}", ValueExpressionUtils.parse(exp));
    }
  }
}

