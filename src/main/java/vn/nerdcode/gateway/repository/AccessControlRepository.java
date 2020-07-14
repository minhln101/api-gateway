package vn.nerdcode.gateway.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import vn.nerdcode.gateway.model.AccessControl;

/**
 * Created by Minh Lee on 02/07/2020.
 */
public interface AccessControlRepository extends CrudRepository<AccessControl, String> {

  Optional<AccessControl> findByMethodAndUri(String method, String uri);

  Optional<AccessControl> findByUserIdsContainsAndMethodAndUri(Long userId, String method,
      String uri);
}
