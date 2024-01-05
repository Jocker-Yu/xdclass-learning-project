package com.xdclass.net.xdclass.redis.learning.dao;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述:
 *
 * @author frank.yu
 * @motto 尊重技术,敬畏技术,融入技术
 * @date 2024/1/1 3:58 PM
 */
@Repository
public class VideoDao {
  private static Map<Integer, VideoDO> map = new HashMap<>();

  static {
    map.put(
        1, new VideoDO(1, "工业级PaaS云平台+SpringCloudAlibaba 综合项目实战(完结)", "https://xdclass.net", 1099));
    map.put(2, new VideoDO(2, "玩转新版高性能RabbitMQ容器化分布式集群实战", "https://xdclass.net", 79));
    map.put(
        3, new VideoDO(3, "新版后端提效神器MybatisPlus+SwaggerUI3.X+Lombok", "https://xdclass.net", 49));
    map.put(4, new VideoDO(4, "玩转Nginx分布式架构实战教程 零基础到高级", "https://xdclass.net", 49));
    map.put(5, new VideoDO(5, "ssm新版SpringBoot2.3/spring5/mybatis3", "https://xdclass.net", 49));
    map.put(6, new VideoDO(6, "新一代微服务全家桶AlibabaCloud+SpringCloud实战", "https://xdclass.net", 59));
  }

  /**
   * 模拟从数据库找
   *
   * @param videoId
   * @return
   */
  public VideoDO findDetailById(int videoId) {
    return map.get(videoId);
  }
}
