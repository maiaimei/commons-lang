package cn.maiaimei.commons.lang.utils;

import cn.maiaimei.commons.lang.constants.FileExtensionEnum;
import cn.maiaimei.commons.lang.constants.StringConstants;
import cn.maiaimei.commons.lang.exception.FileSystemOperationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

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
   * Get the classpath file by the given name.
   *
   * @param name the classpath file name, must not be {@code null}
   * @return the classpath file
   */
  public static File getClassPathFile(String name) {
    Assert.hasText(name,
        "name must not be null and must contain at least one non-whitespace character");
    URL url = FileUtils.class.getClassLoader().getResource(name);
    notNull(url, "classpath file does not exist");
    final File file = getFile(url.getFile());
    isTrue(file.exists(), "classpath file does not exist");
    return file;
  }

  /**
   * Reads the contents of a classpath file into a string using the {@code StandardCharsets.UTF_8}.
   * <p>
   * The classpath file is always closed.
   *
   * @param name the name to read, must not be {@code null}
   * @return the classpath file contents, never {@code null}
   */
  public static String readClassPathFileToString(String name) {
    Assert.hasText(name,
        "name must not be null and must contain at least one non-whitespace character");
    final File file = getClassPathFile(name);
    return readFileToString(file);
  }

  /**
   * Create a new file by the given names.
   *
   * @param names the name elements, must not be {@code null}
   * @return a new file
   */
  public static File createFile(String... names) {
    Assert.notNull(names, "names must not be null");
    try {
      final File file = getFile(names);
      createParentDirectories(file);
      isTrue(!Files.exists(file.toPath()), "File '" + file + "' already exist.");
      Files.createFile(file.toPath());
      return file;
    } catch (IOException e) {
      throw new FileSystemOperationException(e);
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
   * Get the file name by the given type, name and paths.
   *
   * @param type  the file type, refer to {@link FileExtensionEnum#getExtension()}
   * @param name  the file name, without file extension
   * @param paths the file paths
   * @return the file name
   */
  public static String getFileName(String type, String name, String... paths) {
    Assert.hasText(type,
        "type must not be null and must contain at least one non-whitespace character");
    Assert.hasText(name,
        "name must not be null and must contain at least one non-whitespace character");
    Assert.notNull(paths, "paths must not be null");
    final String pathname = String.format("%s%s%s%s%s",
        StringUtils.concat(File.separator, paths),
        File.separator,
        name,
        StringConstants.DOT,
        type);
    return normalizePath(pathname);
  }

  /**
   * Constructs a pathname from the set of path elements.
   *
   * @param paths the path elements.
   * @return the pathname.
   */
  public static String getFilePath(String... paths) {
    Assert.notNull(paths, "paths must not be null");
    final String path = StringUtils.concat(File.separator, paths);
    return normalizePath(path);
  }

  /**
   * Get an existing file or create a new file by the given names.
   *
   * @param names the names to get or create, must not be {@code null}
   * @return a file
   */
  public static File getOrCreateFile(final String... names) {
    Assert.notNull(names, "names must not be null");
    final File file = getFile(names);
    if (Objects.nonNull(file) && !file.exists()) {
      try {
        createParentDirectories(file);
        Files.createFile(file.toPath());
      } catch (IOException e) {
        throw new FileSystemOperationException(e);
      }
    }
    return file;
  }

  /**
   * Convert the specified file to a byte array.
   *
   * @param file the file to get byte array, must not be {@code null}
   * @return a byte array
   */
  public static byte[] getBytes(File file) {
    Assert.notNull(file, "file must not be null");
    byte[] bytes;
    try (FileInputStream fis = new FileInputStream(file)) {
      bytes = new byte[fis.available()];
      fis.read(bytes);
    } catch (IOException e) {
      throw new FileSystemOperationException(e);
    }
    return bytes;
  }

  /**
   * Convert the specified file to a byte array.
   *
   * @param name the name to get byte array, must not be {@code null}
   * @return a byte array
   */
  public static byte[] getBytes(String name) {
    Assert.hasText(name,
        "name must not be null and must contain at least one non-whitespace character");
    byte[] bytes;
    try (FileInputStream fis = new FileInputStream(name)) {
      bytes = new byte[fis.available()];
      fis.read(bytes);
    } catch (IOException e) {
      throw new FileSystemOperationException(e);
    }
    return bytes;
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
    Assert.notNull(file, "file must not be null");
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
    Assert.notNull(file, "file must not be null");
    Assert.notNull(charset, "charset must not be null");
    try {
      return org.apache.commons.io.FileUtils.readFileToString(file, charset);
    } catch (IOException e) {
      throw new FileSystemOperationException(e);
    }
  }

  /**
   * Writes a String to a file creating the file if it does not exist.
   *
   * @param name    the name to write
   * @param data    the content to write to the file
   * @param charset the charset to use, {@code null} means platform default
   */
  public static void writeStringToFile(final String name, final String data,
      final Charset charset) {
    Assert.hasText(name,
        "name must not be null and must contain at least one non-whitespace character");
    Assert.hasText(data,
        "data must not be null and must contain at least one non-whitespace character");
    Assert.notNull(charset, "charset must not be null");
    final File file = getOrCreateFile(name);
    try {
      org.apache.commons.io.FileUtils.writeStringToFile(file, data, charset);
    } catch (IOException e) {
      throw new FileSystemOperationException(e);
    }
  }

  /**
   * Creates all parent directories for a File object.
   *
   * @param file the file that may need parents.
   */
  public static void createParentDirectories(final File file) {
    Assert.notNull(file, "file must not be null");
    if (Objects.nonNull(file.getParentFile())
        && Files.isDirectory(file.getParentFile().toPath())
        && !Files.exists(file.getParentFile().toPath())) {
      isTrue(file.getParentFile().mkdirs(),
          "Cannot create directory '" + file.getParentFile() + "'.");
    }
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
      throw new FileSystemOperationException(e);
    }
  }

  /**
   * Get an existing directory or create a new directory by the given names.
   *
   * @param names the names to get or create, must not be {@code null}
   * @return a directory
   */
  public static File getOrCreateDirectory(final String... names) {
    final File file = getFile(names);
    if (!file.exists()) {
      isTrue(file.mkdirs(), String.format("Directory %s created failed", file.getAbsolutePath()));
    }
    return file;
  }

  /**
   * Returns a list of file in the given path.
   *
   * @param path the path to list files
   * @return a list of file
   */
  public static List<File> listFiles(String path) {
    final File file = getFile(path);
    if (file.exists() && file.isDirectory()) {
      final File[] files = file.listFiles();
      if (Objects.nonNull(files)) {
        return Arrays.stream(files).collect(Collectors.toList());
      }
    }
    return Collections.emptyList();
  }

  /**
   * Rename file from src to dest.
   *
   * @param src  the source file name
   * @param dest the destination file name
   * @return true if and only if the renaming succeeded; false otherwise
   */
  public static boolean renameTo(String src, String dest) {
    final File srcFile = getFile(src);
    final File destFile = getFile(dest);
    return srcFile.renameTo(destFile);
  }

  /**
   * Moves a file preserving attributes.
   *
   * @param srcFile  the file to be moved.
   * @param destFile the destination file.
   */
  public static void moveFile(final String srcFile, final String destFile) {
    moveFile(getFile(srcFile), getFile(destFile));
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
      throw new FileSystemOperationException(e);
    }
  }

  /**
   * Normalize the path by suppressing sequences like "path/.." and inner simple dots.
   *
   * @param path the name to normalize, must not be {@code null}
   * @return the normalize path
   */
  public static String normalizePath(String path) {
    Assert.hasText(path,
        "name must not be null and must contain at least one non-whitespace character");
    final String cleanPath = StringUtils.cleanPath(path);
    return cleanPath.replaceAll("/+", "/");
  }

  /**
   * Assert that an object is not {@code null}.
   *
   * @param object  the object to check
   * @param message the exception message to use if the assertion fails
   * @throws FileSystemOperationException if the object is {@code null}
   */
  public static void notNull(@Nullable Object object, String message) {
    if (object == null) {
      throw new FileSystemOperationException(message);
    }
  }

  /**
   * Assert a boolean expression, throwing an {@code FileSystemOperationException} if the expression
   * evaluates to {@code false}.
   *
   * @param expression a boolean expression
   * @param message    the exception message to use if the assertion fails
   * @throws FileSystemOperationException if {@code expression} is {@code false}
   */
  public static void isTrue(boolean expression, String message) {
    if (!expression) {
      throw new FileSystemOperationException(message);
    }
  }

}
