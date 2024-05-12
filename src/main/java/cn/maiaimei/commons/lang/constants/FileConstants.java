package cn.maiaimei.commons.lang.constants;

import java.io.File;

/**
 * Miscellaneous {@link File} constants.
 */
public final class FileConstants {

  /**
   * Private constructors ensure that classes cannot be instantiated
   */
  private FileConstants() {
    throw new UnsupportedOperationException();
  }

  public static final String TXT = "txt";
  public static final String CSV = "csv";
  public static final String JSON = "json";
  public static final String DOC = "doc";
  public static final String DOCX = "docx";
  public static final String XLS = "xls";
  public static final String XLSX = "xlsx";
  public static final String PDF = "pdf";
  public static final String ZIP = "zip";
}
