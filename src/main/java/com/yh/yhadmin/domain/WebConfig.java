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
    private String mainNotice;

    @Column
    private String subNotice;

    @Column
    private String copyRight;

    @Column
    private String showStock;

    @Column
    private String wbeStyle;

    @Column
    private String logo;
    @Column
    private String bgImg;

    public WebConfig() {
    }

    public WebConfig(String mainTitle, String subTitle, String titleDesc, String keyWords, String mainNotice, String subNotice, String copyRight, String showStock, String wbeStyle, String logo, String bgImg) {
        this.mainTitle = mainTitle;
        this.subTitle = subTitle;
        this.titleDesc = titleDesc;
        this.keyWords = keyWords;
        this.mainNotice = mainNotice;
        this.subNotice = subNotice;
        this.copyRight = copyRight;
        this.showStock = showStock;
        this.wbeStyle = wbeStyle;
        this.logo = logo;
        this.bgImg = bgImg;
    }
}
