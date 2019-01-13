package com.yh.yhadmin.domain;

import com.yh.yhadmin.domain.domain.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/13 16:52
 * @Company Matt
 */
@Data
@Entity
@Table(name = "operatelogs")
@EqualsAndHashCode(callSuper = true)
public class OperateLogs extends Model {

    @Column(columnDefinition = "varchar(50) COMMENT '操作名称'")
    private String optName;

    @Column(columnDefinition = "varchar(50) COMMENT '操作人'")
    private String optUser;

    @Column(columnDefinition = "varchar(50) COMMENT '操作人id'")
    private String optUserId;

    @Column(columnDefinition = "varchar(50) COMMENT '操作人ip'")
    private String optIp;

    @Column(columnDefinition = "varchar(50) COMMENT '操作描述'")
    private String optDesc;

    @Column(columnDefinition = "varchar(255) COMMENT '请求参数'")
    private String optParams;

}
