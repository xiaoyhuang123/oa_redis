package com.ft.backend.work.baseVo;

import java.io.Serializable;

/**
 * Created by huanghongyi on 2017/11/4.
 */
public class ListItemVo implements Serializable {
    private String text;
    private String value;

    public ListItemVo(){
    }

    public ListItemVo(String text, String value){
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ListItemVo{");
        sb.append("text='").append(text).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
