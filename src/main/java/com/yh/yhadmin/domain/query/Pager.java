package com.yh.yhadmin.domain.query;

import lombok.Data;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/1/16 20:16
 */
public class Pager {

    private int start = 0;

    private int size = 10;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        start--;
        if(start < 0)
            start = 0;
        this.start = start;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
