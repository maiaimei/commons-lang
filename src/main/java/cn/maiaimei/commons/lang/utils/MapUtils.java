package cn.maiaimei.commons.lang.utils;

import com.google.common.collect.Maps;
import java.util.Map;

public final class MapUtils {

  private MapUtils() {
    throw new UnsupportedOperationException();
  }

  public static <K, V> Map<K, V> of(K k1, V v1) {
    Map<K, V> map = Maps.newHashMap();
    map.put(k1, v1);
    return map;
  }

  public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2) {
    Map<K, V> map = Maps.newHashMap();
    map.put(k1, v1);
    map.put(k2, v2);
    return map;
  }

  public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
    Map<K, V> map = Maps.newHashMap();
    map.put(k1, v1);
    map.put(k2, v2);
    map.put(k3, v3);
    return map;
  }

  public static <K, V> Builder<K, V> builder() {
    return new Builder<>();
  }

  public static class Builder<K, V> {

    private final Map<K, V> map;

    public Builder() {
      map = Maps.newHashMap();
    }

    public Builder<K, V> of(K k1, V v1) {
      map.put(k1, v1);
      return this;
    }

    public Map<K, V> build() {
      return map;
    }
  }

}
