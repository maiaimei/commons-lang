package cn.maiaimei.commons.lang.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import cn.maiaimei.commons.lang.constants.StringConstants;
import org.junit.jupiter.api.Test;

public class StringUtilsTest {

  @Test
  public void testConcat() {
    assertEquals("path/to/destination",
        StringUtils.concat(StringConstants.SLASH, "path", "to", "destination"));
    assertNull(StringUtils.concat(null, "path", "to", "destination"));
    assertNull(StringUtils.concat("", "path", "to", "destination"));
    assertEquals("path to destination", StringUtils.concat(" ", "path", "to", "destination"));
  }
}
