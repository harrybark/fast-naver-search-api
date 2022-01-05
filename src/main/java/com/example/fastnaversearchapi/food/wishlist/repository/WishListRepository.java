package com.example.fastnaversearchapi.food.wishlist.repository;

import com.example.fastnaversearchapi.food.db.MemoryDbRepositoryAbstract;
import com.example.fastnaversearchapi.food.wishlist.entity.WishListEntity;
import org.springframework.stereotype.Repository;

@Repository
public class WishListRepository extends MemoryDbRepositoryAbstract<WishListEntity> {

}
