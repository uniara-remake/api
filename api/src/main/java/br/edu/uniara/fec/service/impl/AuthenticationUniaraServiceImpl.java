package br.edu.uniara.fec.service.impl;

import br.edu.uniara.fec.config.UniaraVirtualConfig;
import br.edu.uniara.sdk.client.Client;
import br.edu.uniara.sdk.client.VirtualClient;
import br.edu.uniara.sdk.entity.Authorization;
import br.edu.uniara.sdk.entity.LoginRequest;
import br.edu.uniara.fec.service.AuthenticationService;
import br.edu.uniara.sdk.exception.InvalidCredentialsException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.net.URI;

@Service
public class AuthenticationUniaraServiceImpl implements AuthenticationService {

  @Autowired
  private UniaraVirtualConfig uniaraVirtualConfig;

  private Client virtualClient;

  @PostConstruct
  public void initialize() {
    virtualClient = VirtualClient.getInstance(URI.create(uniaraVirtualConfig.getUrl()));
  }

  @Override
  public Authorization login(@NonNull LoginRequest loginRequest) throws InvalidCredentialsException {

    Assert.notNull(loginRequest, "Object with authentication must be specified");
    Assert.notNull(loginRequest.getUsername(), "Username must be specified");
    Assert.notNull(loginRequest.getPassword(), "Password must be specified");

    return virtualClient.login(loginRequest.getUsername(), loginRequest.getPassword());
  }
}
