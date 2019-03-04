package com.yh.yhadmin.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yh.yhadmin.domain.base.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Describle This Class Is 商品表
 * @Author ZengMin
 * @Date 2019/1/16 20:09
 */
@Data
public class GoodsVo extends Model {

    private String id;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate = new Date();

    private String name;

    private String goodsDesc;

    private String img;

    private String cid;

    private Integer status;

    private Double price;

    private Boolean needPwd;

    private String pullPwd;

    private String cname;

    private long kmCount;

    private Integer sort;
}
