package cn.maiaimei.commons.lang.exception;

public class JsonOperationException extends RuntimeException {

  public JsonOperationException() {
  }

  public JsonOperationException(String message) {
    super(message);
  }

  public JsonOperationException(String message, Throwable cause) {
    super(message, cause);
  }

  public JsonOperationException(Throwable cause) {
    super(cause);
  }
}
