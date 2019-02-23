package com.yh.yhadmin.repository;

import com.yh.yhadmin.domain.WebConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/2/3 0003 15:58
 * @Company
 */
public interface WebConfigRepository extends JpaRepository<WebConfig,String>{

    WebConfig findAllById(String id);

    @Transactional
    @Modifying
    @Query("update WebConfig c set c.adminEmail = ?1 ")
    int updateAdminEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO `webconfig`(`id`, `createDate`, `bgImg`, `copyRight`, `keyWords`, `logo`, " +
            "`mainNotice`, `mainTitle`, `showStock`, `subNotice`, `subTitle`, `titleDesc`, `wbeStyle`) " +
            "VALUES (?1, null, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL);",nativeQuery = true)
    int insert(String id);

}
