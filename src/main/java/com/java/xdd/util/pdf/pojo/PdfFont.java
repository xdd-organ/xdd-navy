package com.java.xdd.util.pdf.pojo;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/11.
 */
public enum PdfFont {
    A(0),B(1),C(2),D(3);

    public List<Font> fonts = new ArrayList<>();
    public static BaseFont baseFont = null;
    static {
        try {
            baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    {
        try {
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            fonts.add(new Font(baseFont, 8, Font.NORMAL)); //
            fonts.add(new Font(baseFont, 10, Font.BOLD)); //
            fonts.add(new Font(baseFont, 8, Font.BOLD)); //
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Font font;

    PdfFont(int i) {
        this.font = fonts.get(i);
    }

    public Font getFont() {
        return font;
    }
}
