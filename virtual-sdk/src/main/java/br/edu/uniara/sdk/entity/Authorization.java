package br.edu.uniara.sdk.entity;

import lombok.Builder;
import lombok.Value;

/**
 * Tokens de autenticação e informações relacionadas ao login.
 *
 * @author Leonardo Elias de Oliveira.
 */
@Value
@Builder
public class Authorization {
    String sessionId;
}
