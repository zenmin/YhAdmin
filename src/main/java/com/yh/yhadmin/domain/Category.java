package com.yh.yhadmin.domain;

import com.yh.yhadmin.domain.base.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @Describle This Class Is 商品分类
 * @Author ZengMin
 * @Date 2019/1/15 19:57
 */
@Entity
@Table(name = "category")
@Data
@EqualsAndHashCode(callSuper = true)
public class Category extends Model {

   @Column
   private String  name;

   @Column
   private String  categoryDesc;

   @Column(columnDefinition = "int(11) DEFAULT '0' COMMENT '排序'")
   private Integer sort = 0;

   @Column(columnDefinition = "int(11) COMMENT '1启用 2禁用'")
   private Integer status;

}
