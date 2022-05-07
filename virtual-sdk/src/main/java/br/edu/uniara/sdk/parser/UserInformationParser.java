package br.edu.uniara.sdk.parser;

import br.edu.uniara.sdk.entity.UserInfo;
import lombok.NonNull;
import lombok.val;
import org.jsoup.Jsoup;

public class UserInformationParser implements Parser<String, UserInfo> {

    @Override
    public @NonNull UserInfo parse(@NonNull String html) {
        val document = Jsoup.parse(html);

        val elements = document.select("body > table > tbody > tr:nth-child(7) > td > table > tbody > tr > td:nth-child(3) > table > tbody > tr > td > table > tbody")
            .select("tr > td");

        return UserInfo.builder()
            .fullName(elements.get(2).text())
            .build();
    }

}
