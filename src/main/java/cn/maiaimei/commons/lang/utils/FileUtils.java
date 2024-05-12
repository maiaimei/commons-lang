package cn.maiaimei.commons.lang.utils;

import cn.maiaimei.commons.lang.constants.NumericConstants;
import cn.maiaimei.commons.lang.constants.StringConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public final class FileUtils {

  private FileUtils() {
    throw new UnsupportedOperationException();
  }

  public static File getClassPathFile(String pathname) {
    URL url = FileUtils.class.getClassLoader().getResource(pathname);
    Assert.notNull(url, "classpath file does not exist");
    final File file = new File(url.getFile());
    if (!file.exists()) {
      Assert.notNull(url, "classpath file does not exist");
    }
    return file;
  }

  public static String getClassPathFilename(String pathname) {
    URL url = FileUtils.class.getClassLoader().getResource(pathname);
    Assert.notNull(url, "classpath file does not exist");
    final File file = new File(url.getFile());
    if (!file.exists()) {
      Assert.notNull(url, "classpath file does not exist");
    }
    return file.getAbsolutePath();
  }

  public static String readClassPathFileToString(String pathname) {
    final File file = getClassPathFile(pathname);
    try {
      return org.apache.commons.io.FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static String readFileToString(File file) {
    try {
      return org.apache.commons.io.FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void writeStringToFile(String pathname, String value) {
    try (Writer writer = new OutputStreamWriter(
        Files.newOutputStream(Paths.get(pathname)),
        StandardCharsets.UTF_8)) {
      writer.write(value);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static File createFile(String pathname) {
    return new File(pathname);
  }

  public static File createRandomFile(String destination, String suffix) {
    return createFile(getRandomFilename(destination, suffix));
  }

  public static String getRandomFilename(String destination, String suffix) {
    return String.format("%s%s.%s", destination,
        RandomStringUtils.randomAlphanumeric(NumericConstants.TWELVE),
        StringUtils.trimLeadingCharacter(suffix,
            StringConstants.DOT.charAt(NumericConstants.ZERO)));
  }

  public static File getOrCreateFile(String pathname) {
    File file = new File(pathname);
    if (!file.exists()) {
      file = createFile(pathname);
    }
    return file;
  }

  public static byte[] getBytes(File file) throws IOException {
    byte[] bytes;
    try (FileInputStream fis = new FileInputStream(file)) {
      bytes = new byte[fis.available()];
      fis.read(bytes);
    }
    return bytes;
  }

  public static byte[] getBytes(String pathname) throws IOException {
    byte[] bytes;
    try (FileInputStream fis = new FileInputStream(pathname)) {
      bytes = new byte[fis.available()];
      fis.read(bytes);
    }
    return bytes;
  }


}