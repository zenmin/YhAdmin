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
@Table(name = "webconfig")
@Entity
@EqualsAndHashCode(callSuper = true)
public class WebConfig extends Model {

    @Column(columnDefinition = "varchar(255) COMMENT '主标题'")
    private String mainTitle;

    @Column(columnDefinition = "varchar(255) COMMENT '副标题'")
    private String subTitle;

    @Column(columnDefinition = "varchar(255) COMMENT '标题描述'")
    private String titleDesc;

    @Column(columnDefinition = "varchar(255) COMMENT '网站关键词'")
    private String keyWords;

    @Column(columnDefinition = "text COMMENT '主页公告'")
    private String mainNotice;

    @Column(columnDefinition = "text COMMENT '订单页面公告'")
    private String subNotice;

    @Column(columnDefinition = "varchar(255) COMMENT '版权'")
    private String copyRight;

    @Column(columnDefinition = "int(1) default 1 COMMENT '是否显示库存'")
    private Integer showStock;

    @Column(columnDefinition = "varchar(255) COMMENT '模板路径'")
    private String wbeStyle;

    @Column(columnDefinition = "varchar(255) COMMENT '网站logo'")
    private String logo;

    @Column(columnDefinition = "varchar(255) COMMENT '网站背景图'")
    private String bgImg;

    public WebConfig(String id) {
        super.setId(id);
    }
    public WebConfig() {
    }
}
