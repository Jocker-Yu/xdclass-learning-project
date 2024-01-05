package com.xdclass.net.xdclass.redis.learning.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 功能描述:
 *
 * @author frank.yu
 * @motto 尊重技术,敬畏技术,融入技术
 * @date 2024/1/1 4:02 PM
 */
public class JsonUtil {
  // 定义jackson对象
  private static final ObjectMapper MAPPER = new ObjectMapper();

  /**
   * 将对象转换成json字符串。
   *
   * @return
   */
  public static String objectToJson(Object data) {
    try {
      String string = MAPPER.writeValueAsString(data);
      return string;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 将json结果集转化为对象
   *
   * @param jsonData json数据
   * @param clazz 对象中的object类型
   * @return
   */
  public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
    try {
      T t = MAPPER.readValue(jsonData, beanType);
      return t;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
