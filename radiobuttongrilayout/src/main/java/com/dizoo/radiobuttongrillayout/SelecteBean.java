package com.dizoo.radiobuttongrillayout;

public class SelecteBean {

    private String name;
    private boolean isSelected;

    public SelecteBean(String name) {
        this.name = name;
    }

    public SelecteBean(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
