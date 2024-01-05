package com.xdclass.net.xdclass.redis.learning.service;

import com.xdclass.net.xdclass.redis.learning.model.ProductDO;

import java.util.Map;

/**
 * 功能描述:
 *
 * @author frank.yu
 * @motto 尊重技术,敬畏技术,融入技术
 * @date 2024/1/1 9:10 PM
 */
public interface ProductService  {

  int save(ProductDO productDO);

  int delById(int id);

  ProductDO updateById(ProductDO productDO);

  ProductDO findById(int id);

  Map<String, Object> page(int page, int size);
}
