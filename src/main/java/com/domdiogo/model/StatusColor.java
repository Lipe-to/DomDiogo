package com.domdiogo.model;

public enum StatusColor {
    RED("red"),
    GREEN("green");

    private final String value;

    StatusColor(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
