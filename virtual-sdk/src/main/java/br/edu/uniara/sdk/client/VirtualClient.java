package br.edu.uniara.sdk.client;

import br.edu.uniara.sdk.entity.Authorization;
import br.edu.uniara.sdk.entity.LoginResponse;
import br.edu.uniara.sdk.entity.UserInfo;
import br.edu.uniara.sdk.exception.InvalidCredentialsException;
import br.edu.uniara.sdk.parser.UserInformationParser;
import br.edu.uniara.sdk.util.FormData;
import java.io.IOException;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class VirtualClient implements Client {

    private final @NonNull URI baseUrl;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public static VirtualClient getInstance(@NonNull URI baseUrl) {
        return new VirtualClient(baseUrl);
    }

    @Override
    public Authorization login(@NonNull String username, @NonNull String password) throws InvalidCredentialsException {
        try {
            val sessionId = initializeSessionId();
            val data = new FormData();

            data.put("username", username);
            data.put("senha", password);

            val response = httpClient.send(
                HttpRequest.newBuilder()
                    .POST(BodyPublishers.ofString(data.toString()))
                    .uri(baseUrl.resolve("/login"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Cookie", "PHPSESSID=" + sessionId)
                    .build(),
                BodyHandlers.discarding()
            );

            val info = getLoginInfoFromResponse(response);

            if (response.statusCode() == HttpURLConnection.HTTP_OK && Objects.nonNull(info.getTipoAcesso())) {
                return Authorization.builder()
                    .sessionId(sessionId)
                    .build();
            } else if (response.statusCode() == HttpURLConnection.HTTP_OK && Objects.isNull(info.getTipoAcesso())) {
                throw new InvalidCredentialsException();
            }

            throw new IllegalStateException("An unknown error occurred during authentication");
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException("Error on communicating between mapper and Uniara Virtual");
        }
    }

    @Override
    public byte[] getHomePicture(@NonNull Authorization authorization) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public UserInfo getUserInformation(@NonNull Authorization authorization) {

        try {
            val response = httpClient.send(
                HttpRequest.newBuilder()
                    .uri(baseUrl.resolve("/alunos/consultas/dados/"))
                    .header("Cookie", "PHPSESSID=" + authorization.getSessionId())
                    .build(),
                BodyHandlers.ofString()
            );

            return new UserInformationParser().parse(response.body());
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException("Error during establish communication with Uniara virtual");
        }
    }

    private @NonNull String initializeSessionId() throws IOException, InterruptedException {
        val indexResponse = httpClient.send(
            HttpRequest.newBuilder()
                .GET()
                .uri(baseUrl.resolve("/login"))
                .build(),
            BodyHandlers.discarding()
        );

        val sessionId = getCookieByName(indexResponse, "PHPSESSID");

        return sessionId.orElseThrow(() -> {throw new IllegalStateException("SessionID not found at initialization"); })
            .getValue();
    }

    private Optional<HttpCookie> getCookieByName(@NonNull HttpResponse<?> response, @NonNull String name) {

        val map = new HashMap<String, String>();
        val cookies = response.headers()
            .allValues("set-cookie");

        cookies.forEach(cookie -> {
            val key = cookie.substring(0, cookie.indexOf("="));
            map.put(key, cookie);
        });

        return Optional.ofNullable(map.get(name))
            .map(HttpCookie::parse)
            .orElse(new ArrayList<>()).stream().findFirst();
    }

    private String getCookieValueByName(@NonNull HttpResponse<?> response, @NonNull String name) {
        return getCookieByName(response, name)
            .map(HttpCookie::getValue)
            .orElse(null);
    }

    private LoginResponse getLoginInfoFromResponse(@NonNull HttpResponse<?> response) {
        return LoginResponse.builder()
            .ckVirtual(getCookieValueByName(response, "CKVIRTUAL"))
            .UVXS233E3(getCookieValueByName(response, "UVXS233E3"))
            .tipoAcesso(getCookieValueByName(response, "tipoAcesso"))
            .build();
    }
}
