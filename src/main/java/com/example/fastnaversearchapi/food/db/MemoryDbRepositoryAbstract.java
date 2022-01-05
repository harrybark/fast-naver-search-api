package com.example.fastnaversearchapi.food.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class MemoryDbRepositoryAbstract<T extends MemoryDbEntity> implements MemoryDbRepositoryIfs<T> {

    private final List<T> db = new ArrayList<>();
    private int index = 0;

    @Override
    public Optional<T> findById(int index) {
        return db.stream().filter(t -> t.getIndex() == index).findFirst();
    }

    @Override
    public T save(T entity) {
        var optionalEntity = db.stream().filter(i -> i.getIndex() == entity.getIndex()).findFirst();

        // db에 데이터가 이미 있는 경우
        if(optionalEntity.isEmpty()){
            index++;
            entity.setIndex(index);
            db.add(entity);
            return entity;
        }
        // db에 데이터가 없는 경우
        else {
            var preIndex = optionalEntity.get().getIndex();
            deleteById(preIndex);
            db.add(entity);
            return entity;
        }
    }

    @Override
    public void deleteById(int index) {
        var optionalEntity = db.stream().filter(it -> it.getIndex() == index).findFirst();
        if ( optionalEntity.isPresent() ) {
            db.remove(optionalEntity.get());
        }
    }

    @Override
    public List<T> listAll() {
        return db;
    }
}
