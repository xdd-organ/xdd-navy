package com.java.xdd.util.pdf.pojo;

/**
 * Created by Administrator on 2017/11/11.
 */
public class OffsetTable extends Offset {
    private static final long serialVersionUID = 411613982676067611L;
    private float width;
    private float high;
    private int pageIndex;

    private float totalWidth;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public float getTotalWidth() {
        return totalWidth;
    }

    public void setTotalWidth(float totalWidth) {
        this.totalWidth = totalWidth;
    }

    public float getWidth() {
        return width;
    }

    public float getHigh() {
        return high;
    }

}
