package com.task.restservice.Page;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PagesRepository extends CrudRepository<Pages, Integer> {
    Set<Pages> findByCategoryId(Integer catId);
}
