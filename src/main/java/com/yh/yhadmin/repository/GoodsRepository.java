package com.yh.yhadmin.repository;

import com.yh.yhadmin.domain.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/16 20:13
 */
public interface GoodsRepository extends JpaRepository<Goods,String> {
}
