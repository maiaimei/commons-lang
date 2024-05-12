package cn.maiaimei.commons.lang.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.Test;

public class MapUtilsTest {

  @Test
  public void testOf1() {
    final Map<String, String> map = MapUtils.of("k1", "v1");
    assertEquals("v1", map.get("k1"));
  }

  @Test
  public void testOf2() {
    final Map<String, String> map = MapUtils.of("k1", "v1", "k2", "v2");
    assertEquals("v1", map.get("k1"));
    assertEquals("v2", map.get("k2"));
  }

  @Test
  public void testOf3() {
    final Map<String, String> map = MapUtils.of("k1", "v1", "k2", "v2", "k3", "v3");
    assertEquals("v1", map.get("k1"));
    assertEquals("v2", map.get("k2"));
    assertEquals("v3", map.get("k3"));
  }

  @Test
  public void testBuilder() {
    final Map<Object, Object> map = MapUtils.builder()
        .of("k1", "v1")
        .of("k2", "v2")
        .of("k3", "v3")
        .of("k4", "v4")
        .build();
    assertEquals("v1", map.get("k1"));
    assertEquals("v2", map.get("k2"));
    assertEquals("v3", map.get("k3"));
    assertEquals("v4", map.get("k4"));
  }

}
