package com.yh.yhadmin.domain.vo;

import com.yh.yhadmin.foundation.constant.CommonConstant;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/17 16:09
 */
@Data
public class TemplateVo {

    private Integer type = CommonConstant.InterfaceConfig.INDEX_STYLE.getCode();

    private String index_style; //当前模板

    private List<Map<String,String>> temps;

    public TemplateVo(String index_style, List<Map<String, String>> temps) {
        this.index_style = index_style;
        this.temps = temps;
    }

    public TemplateVo() {
    }
}
