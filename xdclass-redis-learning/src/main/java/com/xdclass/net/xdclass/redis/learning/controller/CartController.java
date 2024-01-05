package com.xdclass.net.xdclass.redis.learning.controller;

import com.xdclass.net.xdclass.redis.learning.dao.VideoDO;
import com.xdclass.net.xdclass.redis.learning.dao.VideoDao;
import com.xdclass.net.xdclass.redis.learning.util.JsonData;
import com.xdclass.net.xdclass.redis.learning.util.JsonUtil;
import com.xdclass.net.xdclass.redis.learning.vo.CartItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSessionListener;

/**
 * 功能描述:
 *
 * @author frank.yu
 * @motto 尊重技术,敬畏技术,融入技术
 * @date 2024/1/1 4:03 PM
 */
@RestController
@RequestMapping("/api/v1/cart")
public class CartController implements HttpSessionListener {

  @Autowired private VideoDao videoDao;

  @Autowired private RedisTemplate redisTemplate;

  @RequestMapping("addCart")
  public JsonData addCart(int videoId, int buyNum) {
    // 获取购物车
    BoundHashOperations<String, Object, Object> myCart = getMyCartOps();
    Object cacheObj = myCart.get(videoId + "");
    String result = "";
    if (cacheObj != null) {
      result = (String) cacheObj;
    }
    if (cacheObj == null) {
      // 不存在则新建一个购物项
      CartItemVO cartItem = new CartItemVO();
      // 从数据库查询详情，我们这边直接随机写个
      VideoDO videoDO = videoDao.findDetailById(videoId);
      videoDO.setId(videoId);
      cartItem.setPrice(videoDO.getPrice());
      cartItem.setBuyNum(buyNum);
      cartItem.setProductId(videoId);
      cartItem.setProductImg(videoDO.getImg());
      cartItem.setProductTitle(videoDO.getTitle());
      myCart.put(videoId + "", JsonUtil.objectToJson(cartItem));
    } else {
      // 存在则新增数量
      CartItemVO cartItem = JsonUtil.jsonToPojo(result, CartItemVO.class);
      cartItem.setBuyNum(cartItem.getBuyNum() + buyNum);
      myCart.put(videoId + "", JsonUtil.objectToJson(cartItem));
    }
    return JsonData.buildSuccess();
  }

  /**
   * 抽取我的购物车通用方法
   *
   * @return
   */
  private BoundHashOperations<String, Object, Object> getMyCartOps() {
    String cartKey = getCartKey();
    return redisTemplate.boundHashOps(cartKey);
  }

  /**
   * 获取购物车的key
   *
   * @return
   */
  private String getCartKey() {
    // 从拦截器获取 ，这里写死即可，每个用户不一样
    int userId = 88;
    String cartKey = String.format("product:cart:%s", userId);
    return cartKey;
  }
}
