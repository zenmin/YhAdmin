package com.cms.yhadmin.repository;

import com.cms.yhadmin.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/3 21:11
 * @Company Matt
 */
public interface TestRepository extends JpaRepository<Test, String> {

    List<Test> findByName(String name);

    //查年龄大于xxx的
    List<Test> findByBirthAfter(Date birth);

    //查id in
    List<Test> findByIdIn(List<String> ids);

    //修改操作
    @Modifying
    @Transactional
    @Query("update Test t set t.name = ?2 where t.id = ?1") //JPQL
    int updateNameById(String id, String name);

    //本地sql
    @Query(value = "select * from test t where t.name = ?1", nativeQuery = true)
    List<Test> findByName2(String name);
}
