package br.edu.uniara.sdk.parser;

import lombok.NonNull;

public interface Parser<PARSEABLE, PARSED> {

    @NonNull PARSED parse(@NonNull PARSEABLE parseable);

}
