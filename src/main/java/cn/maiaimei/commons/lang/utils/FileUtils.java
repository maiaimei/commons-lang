package cn.maiaimei.commons.lang.utils;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;

import cn.maiaimei.commons.lang.constants.StringConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
   * Create a new file by the given {@code pathname}
   *
   * @param pathname the pathname to create, must not be {@code null}
   * @return a file
   */
  public static File createFile(String... names) {
    notNull(names, "names must not be null");
    try {
      final File file = getFile(names);
      createParentDirectories(file);
      Files.createFile(file.toPath());
      return file;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Construct a file from the set of name elements.
   *
   * @param names the name elements.
   * @return the file.
   */
  public static File getFile(final String... names) {
    return org.apache.commons.io.FileUtils.getFile(names);
  }

  /**
   * Get an existing file or create a new file by the given {@code pathname}
   *
   * @param pathname the pathname to get or create, must not be {@code null}
   * @return a file
   */
  public static File getOrCreateFile(String pathname) {
    hasText(pathname,
        "pathname must not be null and must contain at least one non-whitespace character");
    try {
      Path path = Paths.get(pathname);
      if (!Files.exists(path)) {
        createParentDirectories(path.toFile());
        Files.createFile(path);
      }
      return path.toFile();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Get an existing file or create a new file by the given {@code names}
   *
   * @param names the names to get or create, must not be {@code null}
   * @return a file
   */
  public static File getOrCreateFile(final String... names) {
    final File file = getFile(names);
    if (!file.exists()) {
      try {
        Files.createFile(file.toPath());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    return file;
  }

  /**
   * Creates all parent directories for a Path object.
   *
   * @param path the path that may need parents.
   * @return the parent directory, or {@code null} if the given path does not name a parent
   */
  public static File createParentDirectories(final Path path) {
    notNull(path, "path must not be null");
    return createParentDirectories(path.toFile());
  }

  /**
   * Creates all parent directories for a File object.
   *
   * @param file the file that may need parents.
   * @return the parent directory, or {@code null} if the given file does not name a parent
   */
  public static File createParentDirectories(final File file) {
    notNull(file, "file must not be null");
    try {
      return org.apache.commons.io.FileUtils.createParentDirectories(file);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Get an existing directory or create a new directory by the given {@code names}
   *
   * @param names the names to get or create, must not be {@code null}
   * @return a directory
   */
  public static File getOrCreateDirectory(final String... names) {
    final File file = org.apache.commons.io.FileUtils.getFile(names);
    if (!file.exists()) {
      if (!file.mkdirs()) {
        throw new RuntimeException(
            String.format("Directory %s created failed", file.getAbsolutePath()));
      }
    }
    return file;
  }

  /**
   * Convert the specified file to a byte array
   *
   * @param file the file to get byte array, must not be {@code null}
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
   * @param pathname the pathname to get byte array, must not be {@code null}
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

  /**
   * Get the file name by the given {@code type}, {@code name} and {@code paths}
   *
   * @param type  the file type without prefix dot, for example: pdf
   * @param name  the file name without file extension
   * @param paths the file paths
   * @return the file name
   */
  public static String getFileName(String type, String name, String... paths) {
    hasText(type,
        "type must not be null and must contain at least one non-whitespace character");
    hasText(name,
        "name must not be null and must contain at least one non-whitespace character");
    notNull(paths, "paths must not be null");
    final String pathname = String.format("%s%s%s%s%s",
        StringUtils.concat(File.separator, paths),
        File.separator,
        name,
        StringConstants.DOT,
        type);
    return normalizePath(pathname);
  }

  /**
   * Normalize the path
   *
   * @param pathname the pathname to normalize, must not be {@code null}
   * @return the normalize path
   */
  public static String normalizePath(String pathname) {
    hasText(pathname,
        "pathname must not be null and must contain at least one non-whitespace character");
    final String path = StringUtils.cleanPath(pathname);
    return path.replaceAll("/+", "/");
  }

  /**
   * rename file from {@code src} to {@code dest}
   *
   * @param src  the source file name
   * @param dest the destination file name
   * @return true if and only if the renaming succeeded; false otherwise
   */
  public static boolean renameTo(String src, String dest) {
    final File srcFile = new File(src);
    final File destFile = new File(dest);
    return srcFile.renameTo(destFile);
  }

  /**
   * Returns a list of file in the given path
   *
   * @param path the path to list files
   * @return a list of file
   */
  public static List<File> listFiles(String path) {
    final File file = new File(path);
    if (file.exists() && file.isDirectory()) {
      final File[] files = file.listFiles();
      if (Objects.nonNull(files)) {
        return Arrays.stream(files).collect(Collectors.toList());
      }
    }
    return Collections.emptyList();
  }

  /**
   * Constructs a pathname from the set of path elements.
   *
   * @param paths the path elements.
   * @return the pathname.
   */
  public static String getPath(String... paths) {
    final String path = StringUtils.concat(File.separator, paths);
    return normalizePath(path);
  }

  /**
   * Deletes a directory recursively.
   *
   * @param directory directory to delete
   */
  public static void deleteDirectory(final File directory) {
    try {
      org.apache.commons.io.FileUtils.deleteDirectory(directory);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Moves a file preserving attributes.
   *
   * @param srcFile  the file to be moved.
   * @param destFile the destination file.
   */
  public static void moveFile(final String srcFile, final String destFile) {
    try {
      org.apache.commons.io.FileUtils.moveFile(getOrCreateFile(srcFile), getOrCreateFile(destFile));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Moves a file preserving attributes.
   *
   * @param srcFile  the file to be moved.
   * @param destFile the destination file.
   */
  public static void moveFile(final File srcFile, final File destFile) {
    try {
      org.apache.commons.io.FileUtils.moveFile(srcFile, destFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
