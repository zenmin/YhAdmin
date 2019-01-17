package com.yh.yhadmin.repository;

import com.yh.yhadmin.domain.CardPassword;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/17 20:13
 */
public interface CardPasswordRepository extends JpaRepository<CardPassword,String> {

    List<CardPassword> findByStatusIsFalse(Pageable pageable);

    Integer deleteByStatus(boolean isUse);
}
