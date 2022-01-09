package com.example.fastnaversearchapi.food.wishlist.repository;

import com.example.fastnaversearchapi.food.wishlist.entity.WishListEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WishListRepositoryTest {

    @Autowired
    private WishListRepository wishListRepository;

    private WishListEntity create() {
        var wishList = new WishListEntity();
        wishList.setTitle("title");
        wishList.setCategory("category");
        wishList.setAddress("address");
        wishList.setRoadAddress("roadAddress");
        wishList.setHomePageLink("");
        wishList.setImageLink("");
        wishList.setVisit(false);
        wishList.setVisitCount(0);
        wishList.setLastVisitDate(null);

        return wishList;
    }

    @Test
    public void saveTest(){
        var wishListEntity = create();
        var expectedResult = wishListRepository.save(wishListEntity);
        Assertions.assertNotNull(expectedResult);
        Assertions.assertEquals(1, expectedResult.getIndex());
    }

    @Test
    public void updateTest(){
        var wishListEntity = create();
        var expectedResult = wishListRepository.save(wishListEntity);

        expectedResult.setTitle("test");
        var saveEntity = wishListRepository.save(expectedResult);

        Assertions.assertEquals("test", saveEntity.getTitle());
        Assertions.assertEquals(1, wishListRepository.findAll().size());
    }

    @Test
    public void findByIdTest() {
        var wishListEntity = create();
        wishListRepository.save(wishListEntity);

        var expectedResult = wishListRepository.findById(1);
        Assertions.assertEquals(true, expectedResult.isPresent());
        Assertions.assertEquals(1, expectedResult.get().getIndex());
    }

    @Test
    public void deleteTest() {
        var wishListEntity = create();
        wishListRepository.save(wishListEntity);

        wishListRepository.deleteById(1);

        int count = wishListRepository.findAll().size();

        Assertions.assertEquals(0, count);
    }

    @Test
    public void listAllTest() {
        var wishListEntity1 = create();
        wishListRepository.save(wishListEntity1);

        var wishListEntity2 = create();
        wishListRepository.save(wishListEntity2);

        int count = wishListRepository.findAll().size();

        Assertions.assertEquals(2, count);
    }
}
