package br.edu.uniara.sdk.entity;

import lombok.Builder;
import lombok.Value;

/**
 * Informações do usuário cadastradas na Uniara Virtual
 *
 * @author Leonardo Elias
 */
@Value
@Builder
public class UserInfo {
    String fullName;
}
