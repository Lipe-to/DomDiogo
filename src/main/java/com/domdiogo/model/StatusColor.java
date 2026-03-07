package com.domdiogo.model;

public enum StatusColor {
    RED("#D13E3E"),
    GREEN("#86d1a8");

    private final String value;

    StatusColor(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
