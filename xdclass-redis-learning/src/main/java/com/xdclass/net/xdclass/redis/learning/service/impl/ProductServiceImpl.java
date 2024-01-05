package com.xdclass.net.xdclass.redis.learning.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdclass.net.xdclass.redis.learning.dao.ProductMapper;
import com.xdclass.net.xdclass.redis.learning.model.ProductDO;
import com.xdclass.net.xdclass.redis.learning.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述:
 *
 * @author frank.yu
 * @motto 尊重技术,敬畏技术,融入技术
 * @date 2024/1/1 9:10 PM
 */
@Service
public class ProductServiceImpl implements ProductService {

  @Autowired private ProductMapper productMapper;

  @Cacheable
  @Override
  public int save(ProductDO productDO) {
    return productMapper.insert(productDO);
  }

  @Override
  public int delById(int id) {
    return productMapper.deleteById(id);
  }

  @Override
  @CachePut(
      value = {"product"},
      key = "#productDO.id",
      cacheManager = "cacheManager1Hour")
  public ProductDO updateById(ProductDO productDO) {
    productMapper.updateById(productDO);
    return productDO;
  }

  @Override
  @Cacheable(
      value = {"product"},
      key = "#id",
      cacheManager = "cacheManager1Hour")
  public ProductDO findById(int id) {
    return productMapper.selectById(id);
  }

  @Cacheable(
      value = "page_value",
      key = "#root.methodName+'_'+#page+'_'+#size",
      cacheManager = "cacheManager1Hour")
  @Override
  public Map<String, Object> page(int page, int size) {
    Page<ProductDO> pageInfo = new Page<>(page, size);
    IPage<ProductDO> productDOIPage = productMapper.selectPage(pageInfo, null);
    Map<String, Object> pageMap = new HashMap<>(3);
    pageMap.put("total_record", productDOIPage.getTotal());
    pageMap.put("total_page", productDOIPage.getPages());
    pageMap.put("current_data", productDOIPage.getRecords());
    return pageMap;
  }
}
