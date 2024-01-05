package com.xdclass.net.xdclass.redis.learning.util;

/**
 * 功能描述:
 *
 * @author frank.yu
 * @motto 尊重技术,敬畏技术,融入技术
 * @date 2023/12/31 11:54 AM
 */
public class JsonData {

  /** 状态码 0 表示成功 */
  private Integer code;

  /** 数据 */
  private Object data;

  /** 描述 */
  private String msg;

  public JsonData(int code, Object data, String msg) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  /**
   * 成功，不传入数据
   *
   * @return
   */
  public static JsonData buildSuccess() {
    return new JsonData(0, null, null);
  }

  /**
   * 成功，传入数据
   *
   * @param data
   * @return
   */
  public static JsonData buildSuccess(Object data) {
    return new JsonData(0, data, null);
  }

  /**
   * 失败，传入描述信息
   *
   * @param msg
   * @return
   */
  public static JsonData buildError(String msg) {
    return new JsonData(-1, null, msg);
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
