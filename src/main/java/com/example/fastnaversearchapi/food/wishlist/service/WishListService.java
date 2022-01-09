package com.example.fastnaversearchapi.food.wishlist.service;

import com.example.fastnaversearchapi.food.naver.NaverClient;
import com.example.fastnaversearchapi.food.naver.dto.SearchImageReq;
import com.example.fastnaversearchapi.food.naver.dto.SearchLocalReq;
import com.example.fastnaversearchapi.food.wishlist.dto.WishListDto;
import com.example.fastnaversearchapi.food.wishlist.entity.WishListEntity;
import com.example.fastnaversearchapi.food.wishlist.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListService {

    private final NaverClient naverClient;

    private final WishListRepository wishListRepository;

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

    public WishListDto add(WishListDto wishListDto) {
        //
        var entity = dtoToEntity(wishListDto);
        var saveEntity = wishListRepository.save(entity);
        return entityToDto(saveEntity);

    }

    private WishListEntity dtoToEntity(WishListDto wishListDto){

        var entity = new WishListEntity();
        entity.setTitle(wishListDto.getTitle());
        entity.setCategory(wishListDto.getCategory());
        entity.setIndex(wishListDto.getIndex());
        entity.setRoadAddress(wishListDto.getRoadAddress());
        entity.setImageLink(wishListDto.getImageLink());
        entity.setHomePageLink(wishListDto.getHomePageLink());
        entity.setLastVisitDate(wishListDto.getLastVisitDate());
        entity.setVisitCount(wishListDto.getVisitCount());
        entity.setVisit(wishListDto.isVisit());

        return entity;
    }

    private WishListDto entityToDto(WishListEntity wishListEntity){
        var dto = new WishListDto();
        dto.setIndex(wishListEntity.getIndex());
        dto.setTitle(wishListEntity.getTitle());
        dto.setCategory(wishListEntity.getCategory());
        dto.setAddress(wishListEntity.getAddress());
        dto.setRoadAddress(wishListEntity.getRoadAddress());
        dto.setImageLink(wishListEntity.getImageLink());
        dto.setHomePageLink(wishListEntity.getHomePageLink());
        dto.setLastVisitDate(wishListEntity.getLastVisitDate());
        dto.setVisitCount(wishListEntity.getVisitCount());
        dto.setVisit(wishListEntity.isVisit());

        return dto;
    }

    public List<WishListDto> searchAll() {

        return wishListRepository.findAll().stream().map(it -> entityToDto(it)).collect(Collectors.toList());
    }

    public void delete(int index) {
        wishListRepository.deleteById(index);
    }

    public void addVisit(int index) {
        var wishItem = wishListRepository.findById(index);
        if(wishItem.isPresent()) {
            var item = wishItem.get();
            item.setVisit(true);
            item.setVisitCount(item.getVisitCount() + 1);
        }

    }
}
