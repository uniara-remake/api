package br.edu.uniara.sdk.entity;

import lombok.Builder;
import lombok.Value;

/**
 * Resposta da requisição de login (Uniara Virtual - alunos).
 *
 * @author Leonardo Elias
 */
@Value
@Builder
public class LoginResponse {

    String ckVirtual;
    String UVXS233E3;
    String tipoAcesso;
    String sessionId;
}
