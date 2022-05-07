package br.edu.uniara.sdk.service;

import br.edu.uniara.sdk.client.Client;
import br.edu.uniara.sdk.client.VirtualClient;
import br.edu.uniara.sdk.entity.Authorization;
import br.edu.uniara.sdk.entity.UserInfo;
import br.edu.uniara.sdk.exception.InvalidCredentialsException;
import java.net.URI;
import java.util.Optional;
import lombok.NonNull;
import lombok.val;

public class UniaraVirtualService {

    private static final Client client = VirtualClient.getInstance(URI.create("https://virtual.uniara.com.br"));

    public static UserInfo getUserInformation(@NonNull String username, @NonNull String password)
        throws InvalidCredentialsException {
        val loginResponse = client.login(username, password);

        val sessionId = Optional.ofNullable(loginResponse.getSessionId())
            .orElseThrow(() -> { throw new IllegalStateException("Session ID not found, impossible get user information without an session"); });

        return client.getUserInformation(
            Authorization.builder()
                .sessionId(sessionId)
                .build()
        );
    }
}
