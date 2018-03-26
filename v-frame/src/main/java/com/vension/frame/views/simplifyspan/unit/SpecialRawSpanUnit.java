package com.vension.frame.views.simplifyspan.unit;

import android.text.Spanned;

/**
 * Special Raw Span Unit
 * Created by iWgang on 15/12/3.
 * https://github.com/iwgang/SimplifySpan
 */
public class SpecialRawSpanUnit extends BaseSpecialUnit {
    private Object spanObj;
    private int flags;

    public SpecialRawSpanUnit(String text, Object spanObj) {
        super(text);
        this.spanObj = spanObj;
        this.flags = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
    }

    public SpecialRawSpanUnit(String text, Object spanObj, int flags) {
        super(text);
        this.spanObj = spanObj;
        this.flags = flags;
    }

    public Object getSpanObj() {
        return spanObj;
    }

    public int getFlags() {
        return flags;
    }

}