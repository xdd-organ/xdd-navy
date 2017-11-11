package com.java.xdd.util.pdf.pojo;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/11.
 */
public class Form implements java.io.Serializable{
    private Map<String, String> formDatum;

    public Map<String, String> getFormDatum() {
        return formDatum;
    }

    public void setFormDatum(Map<String, String> formDatum) {
        this.formDatum = formDatum;
    }
}
