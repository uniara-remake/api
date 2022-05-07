package br.edu.uniara.client;

import br.edu.uniara.sdk.client.Client;
import br.edu.uniara.sdk.client.VirtualClient;
import br.edu.uniara.sdk.exception.InvalidCredentialsException;
import java.net.URI;
import java.util.Objects;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VirtualClientTest {

    private final Client client = VirtualClient.getInstance(URI.create("https://virtual.uniara.com.br"));

    @Test
    public void invalidCredentialsLoginTest() {

        val username = System.getenv("UNIARA_USERNAME");
        val password = System.getenv("UNIARA_PASSWORD");

        Objects.requireNonNull(username, "UNIARA_USERNAME deve estar especificado para testes unitários");
        Objects.requireNonNull(password, "UNIARA_PASSWORD deve estar especificado para testes unitários");

        Assertions.assertThrows(
            InvalidCredentialsException.class, () ->
                client.login(
                    username,
                    password
                ));
    }

    @Test
    public void validCredentialsLoginTest() throws InvalidCredentialsException {
        val username = System.getenv("UNIARA_USERNAME");
        val password = System.getenv("UNIARA_PASSWORD");

        Objects.requireNonNull(username, "UNIARA_USERNAME deve estar especificado para testes unitários");
        Objects.requireNonNull(password, "UNIARA_PASSWORD deve estar especificado para testes unitários");

        val info = client.login(
            username,
            password
        );

        Assertions.assertNotNull(info.getSessionId());
    }

    @Test
    public void getUserInformationTest() throws InvalidCredentialsException {

        val info = client.getUserInformation(
            client.login("04719010", "6015545")
        );

        Assertions.assertNotNull(info.getFullName());

    }
}
