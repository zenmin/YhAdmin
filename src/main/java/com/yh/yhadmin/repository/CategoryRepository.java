package com.yh.yhadmin.repository;

import com.yh.yhadmin.domain.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/15 19:59
 */
public interface CategoryRepository extends JpaRepository<Category,String> {

    List<Category> findByNameOrderBySortAsc(String name);

    @Query("select c from Category c order by c.sort asc")
    List<Category> findAllOrderBySortAsc();

}
