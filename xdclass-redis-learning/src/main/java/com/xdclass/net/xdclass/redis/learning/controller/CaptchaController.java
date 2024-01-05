package com.xdclass.net.xdclass.redis.learning.controller;

import com.google.code.kaptcha.Producer;
import com.xdclass.net.xdclass.redis.learning.util.CommonUtil;
import com.xdclass.net.xdclass.redis.learning.util.JsonData;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述: 验证码 控制器
 *
 * @author frank.yu
 * @date 2023/12/31 10:45 AM
 */
@RestController
@RequestMapping("api/v1/captcha")
public class CaptchaController {

  @Autowired private StringRedisTemplate redisTemplate;

  @Autowired private Producer captchaProducer;

  /**
   * 获取验证码
   *
   * @param request request
   * @param response response
   */
  @GetMapping("get_captcha")
  public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {
    /*通过google工具生成验证码字符串*/
    String captchaStr = captchaProducer.createText();
    /*存入Redis（使用用户ip和浏览器终端作为用户key）*/
    String captchaKey = getCaptchaKey(request);
    redisTemplate.opsForValue().set(captchaKey, captchaStr, 1, TimeUnit.MINUTES);
    /*返回验证码给页码*/
    try {
      BufferedImage bufferedImage = captchaProducer.createImage(captchaStr);
      ServletOutputStream outputStream = response.getOutputStream();
      ImageIO.write(bufferedImage, "jpg", outputStream);
      outputStream.flush();
      outputStream.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 通过手机号发送验证码
   *
   * @param phone phone
   * @param captcha captcha
   * @param request request
   * @return JsonData
   */
  @GetMapping("send_code")
  public JsonData sendCode(
      @RequestParam(value = "phone") String phone,
      @RequestParam("captcha") String captcha,
      HttpServletRequest request) {
    String captchaKey = getCaptchaKey(request);
    String redisCaptcha = redisTemplate.opsForValue().get(captchaKey);
    if (captcha.equalsIgnoreCase(redisCaptcha)) {
      redisTemplate.delete(captchaKey);
      // 发送验证码
      return JsonData.buildSuccess();
    }
    return JsonData.buildError("验证码错误");
  }

  /**
   * 获取验证码key
   *
   * @param request request
   * @return key
   */
  private String getCaptchaKey(HttpServletRequest request) {
    String ipAddr = CommonUtil.getIpAddr(request);
    String userAgent = request.getHeader("User-Agent");
    return "user-service:captcha:" + CommonUtil.MD5(ipAddr + userAgent);
  }
}
