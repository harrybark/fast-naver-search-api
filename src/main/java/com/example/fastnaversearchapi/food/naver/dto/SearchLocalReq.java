package com.example.fastnaversearchapi.food.naver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchLocalReq {

    private String query = "";

    private int display = 1;

    private int start = 1;

    private String sort = "sim";

    public MultiValueMap<String, String> toMultiValueMap() {
        var multiMap = new LinkedMultiValueMap<String, String>();
        multiMap.add("query", query);
        multiMap.add("display", String.valueOf(display));
        multiMap.add("start", String.valueOf(start));
        multiMap.add("sort", sort);

        return multiMap;
    }
}
