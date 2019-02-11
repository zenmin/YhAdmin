package com.yh.yhadmin.domain;

import com.yh.yhadmin.domain.base.Model;
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
@EqualsAndHashCode(callSuper = true)
public class WebConfig extends Model {

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

    public WebConfig() {
    }

    public WebConfig(String mainTitle, String subTitle, String titleDesc, String keyWords, String images, String qq, String wx, String phone, String footer) {
        this.mainTitle = mainTitle;
        this.subTitle = subTitle;
        this.titleDesc = titleDesc;
        this.keyWords = keyWords;
        this.images = images;
        this.qq = qq;
        this.wx = wx;
        this.phone = phone;
        this.footer = footer;
    }
}
