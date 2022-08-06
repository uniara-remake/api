package br.edu.uniara.fec.controller;

import br.edu.uniara.fec.exception.LoginException;
import br.edu.uniara.fec.service.AuthenticationService;
import br.edu.uniara.sdk.entity.Authorization;
import br.edu.uniara.sdk.entity.LoginRequest;
import br.edu.uniara.sdk.exception.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping("/auth")
  public Authorization authenticate(@RequestBody LoginRequest loginRequest) {
    try {
      return authenticationService.login(loginRequest);
    } catch (InvalidCredentialsException e) {
      throw new LoginException("Invalid credentials");
    }
  }
}
