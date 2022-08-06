package br.edu.uniara.fec.interceptor;

import br.edu.uniara.fec.annotation.isAuthenticated;
import br.edu.uniara.fec.config.UniaraVirtualConfig;
import br.edu.uniara.fec.constants.ApplicationConstants;
import br.edu.uniara.sdk.client.Client;
import br.edu.uniara.sdk.client.VirtualClient;
import br.edu.uniara.sdk.entity.Authorization;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;

/**
 * Interceptor responsavel por interceptar toda requisição para controller ou método
 * que esteja anotado com {@link isAuthenticated} e validar se o request atual
 * está valido e autenticado.
 *
 * @author Leonardo Elias
 */
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

  @Autowired
  private UniaraVirtualConfig uniaraVirtualConfig;

  private Client client;

  @PostConstruct
  private void init() {
     client = VirtualClient.getInstance(URI.create(uniaraVirtualConfig.getUrl()));
  }

  public boolean hasInterface(Object handler) {
    val handlerMethod = (HandlerMethod) handler;

    val methodHasAnnotation = Objects.nonNull(
            handlerMethod.getMethod().getAnnotation(isAuthenticated.class)
    );
    val classHasAnnotation = Objects.nonNull(
            handlerMethod.getBean().getClass().getAnnotation(isAuthenticated.class)
    );

    return methodHasAnnotation || classHasAnnotation;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    if (hasInterface(handler)) {
      val isAuthentiated = isAuthenticated(request);

      if (!isAuthentiated) {
        response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Cannot access the resource, provided authorization is invalid or expired");
      }

      return isAuthentiated;
    }

    return true;
  }

  private boolean isAuthenticated(HttpServletRequest request) {
    val authorizationHeader = request.getHeader(ApplicationConstants.AUTHORIZATION_HEADER);

    return Optional.ofNullable(authorizationHeader)
            .map(sessionId -> client.authenticated(
                    Authorization.builder()
                            .sessionId(sessionId)
                            .build()))
            .orElse(false);
  }

}
