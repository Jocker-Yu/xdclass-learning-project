package com.xdclass.net.xdclass.redis.learning.controller;

import com.xdclass.net.xdclass.redis.learning.util.JsonData;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述: 优惠券控制器
 *
 * @author frank.yu
 * @motto 尊重技术,敬畏技术,融入技术
 * @date 2024/1/1 2:53 PM
 */
@RestController
@RequestMapping("/api/v1/coupon")
public class CouponController {

  @Autowired private StringRedisTemplate redisTemplate;

  @GetMapping("add")
  public JsonData saveCoupon(@RequestParam(value = "coupon_id", required = true) int couponId) {
    // 防止其他线程误删
    String uuid = UUID.randomUUID().toString();
    String lockKey = "lock:coupon:" + couponId;
    lock(couponId, uuid, lockKey);
    return JsonData.buildSuccess();
  }

  private void lock(int couponId, String uuid, String lockKey) {

    // lua脚本
    String script =
        "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
    Boolean nativeLock =
        redisTemplate.opsForValue().setIfAbsent(lockKey, uuid, Duration.ofSeconds(30));
    System.out.println(uuid + "加锁状态:" + nativeLock);
    if (nativeLock) {
      // 加锁成功
      try {
        // TODO 做相关业务逻辑
        TimeUnit.SECONDS.sleep(10L);
      } catch (InterruptedException e) {
      } finally {
        // 解锁
        Long result =
            redisTemplate.execute(
                new DefaultRedisScript<>(script, Long.class), List.of(lockKey), uuid);
        System.out.println("解锁状态:" + result);
      }
    } else {
      // 自旋操作
      try {
        System.out.println("加锁失败，睡眠5秒 进行自旋");
        TimeUnit.MILLISECONDS.sleep(5000);
      } catch (InterruptedException e) {
      }
      // 睡眠一会再尝试获取锁
      lock(couponId, uuid, lockKey);
    }
  }
}
