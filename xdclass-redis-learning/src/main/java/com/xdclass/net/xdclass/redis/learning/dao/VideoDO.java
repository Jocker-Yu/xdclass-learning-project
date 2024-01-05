package com.xdclass.net.xdclass.redis.learning.dao;

/**
 * 功能描述:
 *
 * @author frank.yu
 * @motto 尊重技术,敬畏技术,融入技术
 * @date 2024/1/1 3:59 PM
 */
public class VideoDO {

  private Integer id;

  private String title;

  private String img;

  private Integer price;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public VideoDO(Integer id, String title, String img, Integer price) {
    this.id = id;
    this.title = title;
    this.img = img;
    this.price = price;
  }
}
