package vn.nerdcode.gateway.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Created by Minh Lee on 02/07/2020.
 *
 * This class represents access control configured for each user on Redis, managed by URI and
 * methods.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@RedisHash("GW_SERVICE_ACL")
public class AccessControl implements Serializable {

  private static final long serialVersionUID = 4815899983670845824L;

  @Id
  private String index;
  private String method;
  private String uri;
  private List<Long> userIds;
}
