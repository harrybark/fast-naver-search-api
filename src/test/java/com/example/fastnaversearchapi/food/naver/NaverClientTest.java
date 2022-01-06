package com.example.fastnaversearchapi.food.naver;

import com.example.fastnaversearchapi.food.naver.dto.SearchImageReq;
import com.example.fastnaversearchapi.food.naver.dto.SearchLocalReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NaverClientTest {

    @Autowired
    private NaverClient naverClient;

    @Test
    public void searchLocalTest() {
        var search = new SearchLocalReq();
        search.setQuery("유안타<b>증권</b>");

        var result = naverClient.searchLocal(search);
        System.out.println("result : " + result);
    }

    @Test
    public void searchImageTest() {
        var search = new SearchImageReq();
        search.setQuery("장수갈비");

        var result = naverClient.searchImage(search);
        System.out.println("result : " + result);
    }
}
