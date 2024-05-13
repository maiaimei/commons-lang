package cn.maiaimei.commons.lang.exception;

public class FileSystemOperationException extends RuntimeException {

  public FileSystemOperationException() {
  }

  public FileSystemOperationException(String message) {
    super(message);
  }

  public FileSystemOperationException(String message, Throwable cause) {
    super(message, cause);
  }

  public FileSystemOperationException(Throwable cause) {
    super(cause);
  }
}
