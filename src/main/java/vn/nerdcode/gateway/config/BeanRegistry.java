package vn.nerdcode.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

/**
 * Created by Minh Lee on 30/06/2020.
 */
@Configuration
public class BeanRegistry {

  /**
   * @return lettuce connection factory to create client connects to Redis.
   */
  @Bean
  public LettuceConnectionFactory lettuceConnectionFactory() {
    return new LettuceConnectionFactory();
  }

  /**
   * @return redis template using lettuce client.
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(lettuceConnectionFactory());
    return template;
  }

  /**
   * @return the resource lookup function from directory named as <q>static</q>.
   */
  @Bean
  RouterFunction staticResourceLocator() {
    return RouterFunctions.resources("/portal/**", new ClassPathResource("static/"));
  }
}
