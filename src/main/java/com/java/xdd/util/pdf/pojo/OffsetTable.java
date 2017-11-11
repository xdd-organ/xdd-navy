package com.java.xdd.util.pdf.pojo;

/**
 * Created by Administrator on 2017/11/11.
 */
public class OffsetTable extends Offset {
    private static final long serialVersionUID = 411613982676067611L;
    private float width;
    private float high;

    private float totalWidth;


    public float getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }
}
