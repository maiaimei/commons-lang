package cn.maiaimei.commons.lang.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import cn.maiaimei.commons.lang.constants.FileConstants;
import cn.maiaimei.commons.lang.constants.NumberConstants;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

public class FileUtilsTest {

  @Test
  public void testGetFileName() {
    final String fileName = FileUtils.getFileName(
        FileConstants.PDF,
        RandomStringUtils.randomAlphanumeric(NumberConstants.TWELVE),
        "C:\\Users\\lenovo\\Desktop\\tmp\\", "pdf");
    assertTrue(fileName.endsWith(FileConstants.PDF));
  }
}
