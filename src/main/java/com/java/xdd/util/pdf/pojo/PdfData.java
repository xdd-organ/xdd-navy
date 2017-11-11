package com.java.xdd.util.pdf.pojo;

/**
 * Created by Administrator on 2017/11/11.
 */
public class PdfData implements java.io.Serializable{
    private Form formDatum;
    private Table tableDatum;

    public Form getFormDatum() {
        return formDatum;
    }

    public void setFormDatum(Form formDatum) {
        this.formDatum = formDatum;
    }

    public Table getTableDatum() {
        return tableDatum;
    }

    public void setTableDatum(Table tableDatum) {
        this.tableDatum = tableDatum;
    }
}
