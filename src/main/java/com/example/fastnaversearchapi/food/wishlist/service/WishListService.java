package com.example.fastnaversearchapi.food.wishlist.service;

import com.example.fastnaversearchapi.food.naver.NaverClient;
import com.example.fastnaversearchapi.food.naver.dto.SearchImageReq;
import com.example.fastnaversearchapi.food.naver.dto.SearchLocalReq;
import com.example.fastnaversearchapi.food.wishlist.dto.WishListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListService {

    private final NaverClient naverClient;

    public WishListDto search(String query) {
        // 지역 검색
        var searhLocalReq = new SearchLocalReq();
        searhLocalReq.setQuery(query);
        var searchLocalRes = naverClient.searchLocal(searhLocalReq);

        if ( searchLocalRes.getTotal() > 0 ) {
            var localItem = searchLocalRes.getItems().stream().findFirst().get();

            var imageQuery = localItem.getTitle().replaceAll("<[^>]*>","");

            var searchImageReq = new SearchImageReq();
            searchImageReq.setQuery(imageQuery);

            // 이미지 검색
            var searchImageRes = naverClient.searchImage(searchImageReq);
            if(searchImageRes.getTotal() > 0 ) {
                // 결과 생성
                var imageItem = searchImageRes.getItems().stream().findFirst().get();

                var result = new WishListDto();
                result.setTitle(localItem.getTitle());
                result.setCategory(localItem.getCategory());
                result.setAddress(localItem.getAddress());
                result.setRoadAddress(localItem.getRoadAddress());
                result.setHomePageLink(localItem.getLink());
                result.setImageLink(imageItem.getLink());

                return result;
            }
        }
        return new WishListDto();
    }
}
