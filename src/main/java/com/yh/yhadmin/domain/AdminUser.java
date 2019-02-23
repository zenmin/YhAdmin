package com.yh.yhadmin.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yh.yhadmin.domain.base.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/19 18:38
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "adminuser")
public class AdminUser extends Model {

    @Column(unique = true)
    private String userName;

    @Column
    private String passWord;

    @Column
    private String realName;

    @Column(columnDefinition = "int(11) default 0 COMMENT '是否是超级管理员'")
    private Integer isAdministrator;

    @Column
    private String lastloginIP;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastloginTime;

    @Column
    private String phone;

    @Column
    private String qq;

    @Column
    private String wx;

    @Column
    private String adminEmail;

    @Column(columnDefinition = "int(11) default 1 COMMENT '状态 1启用 2禁用'")
    private Integer status;

}
