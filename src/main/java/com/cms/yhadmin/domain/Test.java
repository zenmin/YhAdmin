package com.cms.yhadmin.domain;

import com.cms.yhadmin.domain.domain.Model;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/3 21:06
 * @Company Matt
 */
@Entity
@Table(name = "test")
@Data
@EqualsAndHashCode(callSuper = true)
public class Test extends Model {

    @Column(columnDefinition = "varchar(20) COMMENT '姓名'")
    private String name;

    @Column(columnDefinition = "bit(1) COMMENT '性别'")
    private Boolean gender;

    @Column(columnDefinition = "datetime COMMENT '年龄'")
    private Date birth;


    public Test(String name, Boolean gender, Date birth) {
        this.name = name;
        this.gender = gender;
        this.birth = birth;
    }

    public Test() {
    }
}
