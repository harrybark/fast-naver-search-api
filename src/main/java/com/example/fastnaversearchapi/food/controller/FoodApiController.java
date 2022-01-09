package com.example.fastnaversearchapi.food.controller;

import com.example.fastnaversearchapi.food.wishlist.dto.WishListDto;
import com.example.fastnaversearchapi.food.wishlist.service.WishListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/food-api/restaurant")
@RequiredArgsConstructor
public class FoodApiController {

    private final WishListService wishListService;

    @GetMapping("/search")
    public WishListDto search(@RequestParam String query) {
        return  wishListService.search(query);
    }

    @PostMapping("/add")
    public WishListDto add(@RequestBody WishListDto wishListDto) {
        log.info("wishListDto : {}", wishListDto);
        return wishListService.add(wishListDto);
    }

    @GetMapping("/all")
    public List<WishListDto> findAll() {
        return wishListService.searchAll();
    }

    @DeleteMapping("/{index}")
    public void delete(@PathVariable int index){
        wishListService.delete(index);
    }

    @PostMapping("/{index}")
    public void addVisit(@PathVariable int index) {
        wishListService.addVisit(index);
    }
}