package com.yh.yhadmin.repository;

import com.yh.yhadmin.domain.Goods;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/16 20:13
 */
public interface GoodsRepository extends JpaRepository<Goods,String> {
}
