package cn.maiaimei.commons.lang.utils;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ValueExpressionUtilsTest {

  @Test
  public void testParse() {
    String exp = "test-in_${currentTimestamp->yyyyMMddHHmmssSSS}${serialNumber->%05d}.txt";
    for (int i = 0; i < 5; i++) {
      log.info("{}", ValueExpressionUtils.parse(exp));
    }
  }

  @Test
  public void testParseWithParams() {
    String exp = "trade.12345.${file_remoteFile}"
        + ".${currentTimestamp->yyyyMMddHHmmssSSS}${serialNumber->%05d}";
    Map<String, String> params = Maps.newHashMap();
    for (int i = 0; i < 5; i++) {
      params.put("file_remoteFile", UUID.randomUUID() + ".txt");
      log.info("{}", ValueExpressionUtils.parse(exp, params));
    }
  }

  @Test
  public void testParseWithAtomicInteger() {
    String exp = "trade.12345.${file_remoteFile}"
        + ".${currentTimestamp->yyyyMMddHHmmssSSS}${serialNumber->%05d}";
    Map<String, String> params = Maps.newHashMap();
    AtomicInteger atomicInteger = new AtomicInteger();
    for (int i = 0; i < 5; i++) {
      params.put("file_remoteFile", UUID.randomUUID() + ".txt");
      log.info("{}", ValueExpressionUtils.parse(exp, params, atomicInteger));
    }
  }

}

