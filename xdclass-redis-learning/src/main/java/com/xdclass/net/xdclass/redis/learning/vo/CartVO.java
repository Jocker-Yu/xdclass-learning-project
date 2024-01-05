package com.xdclass.net.xdclass.redis.learning.vo;

import java.util.List;

/**
 * 功能描述:
 *
 * @author frank.yu
 * @motto 尊重技术,敬畏技术,融入技术
 * @date 2024/1/1 4:08 PM
 */
public class CartVO {
  /** 购物项 */
  private List<CartItemVO> cartItems;

  /** 购物车总价格 */
  private Integer totalAmount;

  /**
   * 总价格
   *
   * @return
   */
  public int getTotalAmount() {
    return cartItems.stream().mapToInt(CartItemVO::getTotalPrice).sum();
  }

  public List<CartItemVO> getCartItems() {
    return cartItems;
  }

  public void setCartItems(List<CartItemVO> cartItems) {
    this.cartItems = cartItems;
  }
}
