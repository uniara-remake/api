package br.edu.uniara.sdk.util;

import java.util.HashMap;
import java.util.stream.Collectors;
import lombok.val;

public class FormData extends HashMap<String, Object> {

    @Override
    public String toString() {
        val keys = keySet();

        return keys.stream()
            .map(key -> String.format("%s=%s", key, get(key)))
            .map(String::valueOf)
            .collect(Collectors.joining("&"));
    }
}
