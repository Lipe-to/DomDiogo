package com.domdiogo.model;

public enum ColorPalette {
    BLUE("#3C71BA"),
    RED("#D13E3E"),
    TEA_BLUE("#8D99D6"),
    PURPLE("#ca93c7"),
    GREEN("#86d1a8"),
    LIME_GREEN("#b0cf89"),
    SMOOTH_RED("#e08383"),
    ORANGE("#dfb381");

    private final String hex;

    ColorPalette(String hex) {
        this.hex = hex;
    }

    public String getHex() {
        return hex;
    }
}