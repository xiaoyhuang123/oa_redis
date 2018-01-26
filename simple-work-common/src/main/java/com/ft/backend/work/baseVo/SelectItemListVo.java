package com.ft.backend.work.baseVo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huanghongyi on 2017/11/5.
 */
public class SelectItemListVo implements Serializable{

    private String id;

    private String text;

    private List<SelectItemListVo> children;

    private Boolean leaf;

    private Boolean expanded;

    public SelectItemListVo(){};

    public SelectItemListVo(String id, String text){
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<SelectItemListVo> getChildren() {
        return children;
    }

    public void setChildren(List<SelectItemListVo> children) {
        this.children = children;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SelectItemListVo{");
        sb.append("id='").append(id).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append(", children=").append(children);
        sb.append(", leaf=").append(leaf);
        sb.append(", expanded=").append(expanded);
        sb.append('}');
        return sb.toString();
    }
}
