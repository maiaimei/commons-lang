package cn.maiaimei.commons.lang.utils;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Miscellaneous {@link File} utility methods.
 */
public final class FileUtils {

  /**
   * Private constructors ensure that classes cannot be instantiated
   */
  private FileUtils() {
    throw new UnsupportedOperationException();
  }

  /**
   * Get the classpath file by the given pathname
   *
   * @param pathname classpath file path
   * @return the classpath file
   */
  public static File getClassPathFile(String pathname) {
    hasText(pathname,
        "pathname must not be null and must contain at least one non-whitespace character");
    URL url = FileUtils.class.getClassLoader().getResource(pathname);
    notNull(url, "classpath file does not exist");
    final File file = new File(url.getFile());
    if (!file.exists()) {
      notNull(url, "classpath file does not exist");
    }
    return file;
  }

  /**
   * Reads the contents of a classpath file into a string using the {@code StandardCharsets.UTF_8}.
   * <p>
   * The classpath file is always closed.
   *
   * @param pathname the pathname to read, must not be {@code null}
   * @return the classpath file contents, never {@code null}
   */
  public static String readClassPathFileToString(String pathname) {
    hasText(pathname,
        "pathname must not be null and must contain at least one non-whitespace character");
    final File file = getClassPathFile(pathname);
    return readFileToString(file);
  }

  /**
   * Reads the contents of a file into a string using the {@code StandardCharsets.UTF_8}.
   * <p>
   * The file is always closed.
   *
   * @param file the file to read, must not be {@code null}
   * @return the file contents, never {@code null}
   */
  public static String readFileToString(File file) {
    notNull(file, "file must not be null");
    return readFileToString(file, StandardCharsets.UTF_8);
  }

  /**
   * Reads the contents of a file into a String.
   * <p>
   * The file is always closed.
   *
   * @param file    the file to read, must not be {@code null}
   * @param charset the name of the requested charset, {@code null} means platform default
   * @return the file contents, never {@code null}
   */
  public static String readFileToString(final File file, final Charset charset) {
    notNull(file, "file must not be null");
    notNull(charset, "charset must not be null");
    try {
      return org.apache.commons.io.FileUtils.readFileToString(file, charset);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Writes a String to a file creating the file if it does not exist.
   *
   * @param pathname the pathname to write
   * @param data     the content to write to the file
   * @param charset  the charset to use, {@code null} means platform default
   */
  public static void writeStringToFile(final String pathname, final String data,
      final Charset charset) {
    hasText(pathname,
        "pathname must not be null and must contain at least one non-whitespace character");
    hasText(data,
        "data must not be null and must contain at least one non-whitespace character");
    notNull(charset, "charset must not be null");
    final File file = getOrCreateFile(pathname);
    try {
      org.apache.commons.io.FileUtils.writeStringToFile(file, data, charset);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Get an existing file or create a new file by the given {@code pathname}
   *
   * @param pathname the pathname to convert, must not be {@code null}
   * @return a file
   */
  public static File getOrCreateFile(String pathname) {
    hasText(pathname,
        "pathname must not be null and must contain at least one non-whitespace character");
    try {
      Path path = Paths.get(pathname);
      if (!Files.exists(path)) {
        Files.createFile(path);
      }
      return path.toFile();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Convert the specified file to a byte array
   *
   * @param file the file to convert, must not be {@code null}
   * @return a byte array
   */
  public static byte[] getBytes(File file) {
    notNull(file, "file must not be null");
    byte[] bytes;
    try (FileInputStream fis = new FileInputStream(file)) {
      bytes = new byte[fis.available()];
      fis.read(bytes);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return bytes;
  }

  /**
   * Convert the specified file to a byte array
   *
   * @param pathname the pathname to convert, must not be {@code null}
   * @return a byte array
   */
  public static byte[] getBytes(String pathname) {
    hasText(pathname,
        "pathname must not be null and must contain at least one non-whitespace character");
    byte[] bytes;
    try (FileInputStream fis = new FileInputStream(pathname)) {
      bytes = new byte[fis.available()];
      fis.read(bytes);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return bytes;
  }


}
