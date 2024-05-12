package cn.maiaimei.commons.lang.utils;

import com.google.common.collect.Maps;
import java.util.Map;

/**
 * Miscellaneous {@link Map} utility methods.
 */
public final class MapUtils {

  /**
   * Private constructors ensure that classes cannot be instantiated
   */
  private MapUtils() {
    throw new UnsupportedOperationException();
  }

  /**
   * Returns a map containing a single mapping.
   *
   * @param k1  the mapping's key
   * @param v1  the mapping's value
   * @param <K> the type of the mapping's key
   * @param <V> the type of the mapping's value
   * @return a Map containing the specified mapping
   */
  public static <K, V> Map<K, V> of(K k1, V v1) {
    Map<K, V> map = Maps.newHashMap();
    map.put(k1, v1);
    return map;
  }

  /**
   * Returns a map containing two mapping.
   *
   * @param k1  the first mapping's key
   * @param v1  the first mapping's value
   * @param k2  the second mapping's key
   * @param v2  the second mapping's value
   * @param <K> the type of the mapping's key
   * @param <V> the type of the mapping's value
   * @return a Map containing the specified mapping
   */
  public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2) {
    Map<K, V> map = Maps.newHashMap();
    map.put(k1, v1);
    map.put(k2, v2);
    return map;
  }

  /**
   * Returns a map containing three mapping.
   *
   * @param k1  the first mapping's key
   * @param v1  the first mapping's value
   * @param k2  the second mapping's key
   * @param v2  the second mapping's value
   * @param k3  the third mapping's key
   * @param v3  the third mapping's value
   * @param <K> the type of the mapping's key
   * @param <V> the type of the mapping's value
   * @return a Map containing the specified mapping
   */
  public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
    Map<K, V> map = Maps.newHashMap();
    map.put(k1, v1);
    map.put(k2, v2);
    map.put(k3, v3);
    return map;
  }

  /**
   * Returns an instance of {@link Builder}
   *
   * @param <K> the type of the mapping's key
   * @param <V> the type of the mapping's value
   * @return an instance of {@link Builder}
   */
  public static <K, V> Builder<K, V> builder() {
    return new Builder<>();
  }

  /**
   * A mutable builder for a {@code Map}.
   *
   * @param <K> the type of the mapping's key
   * @param <V> the type of the mapping's value
   */
  public static class Builder<K, V> {

    /**
     * The map to be built
     */
    private final Map<K, V> map;

    /**
     * Builder constructor
     */
    public Builder() {
      map = Maps.newHashMap();
    }

    /**
     * Adds the specified mapping to the map being built.
     *
     * @param k the mapping's key
     * @param v the mapping's value
     * @return {@link Builder} instance
     */
    public Builder<K, V> of(K k, V v) {
      map.put(k, v);
      return this;
    }

    /**
     * Builds the map, transitioning this builder to the built state.
     *
     * @return the built map
     */
    public Map<K, V> build() {
      return map;
    }
  }

}
