package com.java.xdd.util.pdf.pojo;

/**
 * Created by Administrator on 2017/11/11.
 */
public class Offset implements java.io.Serializable{
    private static final long serialVersionUID = 2590492200913012003L;

    public float getXoff() {
        return xoff;
    }

    public void setXoff(int xoff) {
        this.xoff = xoff;
    }

    public float getYoff() {
        return yoff;
    }

    public void setYoff(int yoff) {
        this.yoff = yoff;
    }

    private float xoff = 0;
    private float yoff = 0;
}
