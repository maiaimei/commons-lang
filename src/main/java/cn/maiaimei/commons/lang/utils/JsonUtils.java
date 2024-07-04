package cn.maiaimei.commons.lang.utils;

import cn.maiaimei.commons.lang.exception.JsonOperationException;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Miscellaneous JSON utility methods.
 */
public final class JsonUtils {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  static {
    OBJECT_MAPPER.setSerializationInclusion(Include.NON_NULL);
    OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, Boolean.FALSE);
    OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE);
    OBJECT_MAPPER.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, Boolean.TRUE);
    OBJECT_MAPPER.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, Boolean.TRUE);
  }

  /**
   * Private constructors ensure that classes cannot be instantiated
   */
  private JsonUtils() {
    throw new UnsupportedOperationException();
  }

  /**
   * Serialize any Java value as a JSON String
   *
   * @param value any Java value
   * @return JSON String
   */
  public static String toJson(Object value) {
    try {
      return OBJECT_MAPPER.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      throw new JsonOperationException(e);
    }
  }

  /**
   * Deserialize a JSON String as the given Java value
   *
   * @param value     JSON String
   * @param valueType the given Java value type
   * @param <T>       the type of the given Java value
   * @return the given Java value
   */
  public static <T> T toObject(String value, Class<T> valueType) {
    try {
      return OBJECT_MAPPER.readValue(value, valueType);
    } catch (JsonProcessingException e) {
      throw new JsonOperationException(e);
    }
  }

  /**
   * Deserialize a JSON String as the given Java value
   *
   * @param value        JSON String
   * @param valueTypeRef the given Java value type
   * @param <T>          the type of the given Java value
   * @return the given Java value
   */
  public static <T> T toObject(String value, TypeReference<T> valueTypeRef) {
    try {
      return OBJECT_MAPPER.readValue(value, valueTypeRef);
    } catch (JsonProcessingException e) {
      throw new JsonOperationException(e);
    }
  }

}
