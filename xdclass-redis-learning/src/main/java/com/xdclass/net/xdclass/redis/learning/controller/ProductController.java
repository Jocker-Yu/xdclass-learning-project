package com.xdclass.net.xdclass.redis.learning.controller;

import com.xdclass.net.xdclass.redis.learning.model.ProductDO;
import com.xdclass.net.xdclass.redis.learning.service.ProductService;
import com.xdclass.net.xdclass.redis.learning.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述:
 *
 * @author frank.yu
 * @motto 尊重技术,敬畏技术,融入技术
 * @date 2024/1/1 9:14 PM
 */
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

  @Autowired private ProductService productService;

  @PostMapping("save")
  public JsonData save(@RequestBody ProductDO productDO) {
    productService.save(productDO);
    return JsonData.buildSuccess();
  }

  @PostMapping("update")
  public JsonData update(@RequestBody ProductDO productDO) {
    productService.updateById(productDO);
    return JsonData.buildSuccess();
  }

  @DeleteMapping("del")
  public JsonData del(@RequestParam("id") Integer id) {
    productService.delById(id);
    return JsonData.buildSuccess();
  }

  @GetMapping("find")
  public JsonData find(@RequestParam("id") Integer id) {
    return JsonData.buildSuccess(productService.findById(id));
  }

  @GetMapping("page")
  public JsonData page(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
    return JsonData.buildSuccess(productService.page(page, size));
  }
}
