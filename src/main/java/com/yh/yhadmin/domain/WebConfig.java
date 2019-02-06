package com.yh.yhadmin.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Describle
 * @Author Li lei
 * @Date 2019/2/3 0003 15:51
 * @Company
 */
@Data
@Table
@Entity
@EqualsAndHashCode
public class WebConfig {

    @Column
    private String mainTitle;

    @Column
    private String subTitle;

    @Column
    private String titleDesc;

    @Column
    private String keyWords;

    @Column
    private String images;

    @Column
    private String qq;

    @Column
    private String wx;

    @Column
    private String phone;

    @Column
    private String footer;
}
