package com.cms.yhadmin.domain;

import com.cms.yhadmin.domain.domain.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/4 20:41
 * @Company Matt
 */
@Data
@Entity
@Table(name = "logs")
@EqualsAndHashCode(callSuper = true)
public class CommonLog extends Model {

    @Column
    private Date date = new Date();

    @Column
    private String reqIp;

    @Column
    private String requestURL;

    @Column
    private String reqParams;

    public CommonLog(String reqIp, String requestURL, String reqParams) {
        this.reqIp = reqIp;
        this.requestURL = requestURL;
        this.reqParams = reqParams;
    }

    public CommonLog() {
    }
}
