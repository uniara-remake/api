package br.edu.uniara.sdk.client;

import br.edu.uniara.sdk.entity.Authorization;
import br.edu.uniara.sdk.entity.UserInfo;
import br.edu.uniara.sdk.exception.InvalidCredentialsException;
import lombok.NonNull;

/**
 * Transporter para enviar requisições REST a
 * Uniara Virtual.
 *
 * @author Leonardo Elias
 */
public interface Client {

    Authorization login(@NonNull String username, @NonNull String password) throws InvalidCredentialsException;

    byte[] getHomePicture(@NonNull Authorization authorization);

    UserInfo getUserInformation(@NonNull Authorization authorization);
}
