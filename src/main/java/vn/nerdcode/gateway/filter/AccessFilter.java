package vn.nerdcode.gateway.filter;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import vn.nerdcode.gateway.repository.AccessControlRepository;

/**
 * Created by Minh Lee on 02/07/2020.
 */
@Slf4j
@Component
public class AccessFilter implements GlobalFilter {

  @Autowired
  private AccessControlRepository accessControlRepository;

  private static final String PING_CONTEXT = "/actuator/info";

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String serviceName = getServiceNameFromExchange(exchange);
    Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
    if (isAlive(exchange.getRequest().getURI())) {
      log.info("Incoming request {} is routed to service {} on port {}",
          exchange.getRequest().getURI(),
          (serviceName != null ? serviceName : "UNKNOWN"),
          (route != null ? route.getMetadata().get("management.port") : "UNKNOWN"));
    }
    return chain.filter(exchange);
  }

  /**
   * @param exchange instance of ServerWebExchange.
   * @return service name detected.
   */
  private String getServiceNameFromExchange(ServerWebExchange exchange) {
    Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
    return route == null ? null : route.getUri().getHost();
  }


  /**
   * Check if URI can connect.
   *
   * @param uri extracted from incoming request.
   * @return boolean result
   */
  private boolean isAlive(URI uri) {
    if (uri == null) {
      return false;
    }

    try {
      URLConnection urlConnection = new URL(uri + PING_CONTEXT).openConnection();
      urlConnection.setConnectTimeout(100);
      urlConnection.setReadTimeout(100);
      urlConnection.connect();
      return true;
    } catch (Exception e) {
      log.info("Failed to ping {}", uri);
      return false;
    }
  }
}
