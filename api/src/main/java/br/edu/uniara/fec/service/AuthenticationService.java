package br.edu.uniara.fec.service;

import br.edu.uniara.sdk.entity.Authorization;
import br.edu.uniara.sdk.entity.LoginRequest;
import br.edu.uniara.sdk.exception.InvalidCredentialsException;
import lombok.NonNull;

public interface AuthenticationService {

  Authorization login(@NonNull LoginRequest loginRequest) throws InvalidCredentialsException;
}
