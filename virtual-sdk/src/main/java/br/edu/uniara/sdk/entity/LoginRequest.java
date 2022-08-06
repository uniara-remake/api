package br.edu.uniara.sdk.entity;

import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * Data provided for authentication in Uniara Virtual.
 *
 * @author Leonardo Elias
 */
@Value
@NoArgsConstructor(force = true)
public class LoginRequest {
  String username;
  String password;
}
