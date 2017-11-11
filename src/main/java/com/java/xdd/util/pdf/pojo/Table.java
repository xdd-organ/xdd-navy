package com.java.xdd.util.pdf.pojo;

import java.util.List;

/**
 * Created by Administrator on 2017/11/11.
 */
public class Table implements java.io.Serializable{
    private static final long serialVersionUID = -2449063452010780396L;

    public List<List<String>> getTableDatum() {
        return tableDatum;
    }

    public void setTableDatum(List<List<String>> tableDatum) {
        this.tableDatum = tableDatum;
    }

    List<List<String>> tableDatum;

    public int getRows() {
        return tableDatum.size();
    }

    public int getColumns() {
        return tableDatum.get(0).size();
    }
}
