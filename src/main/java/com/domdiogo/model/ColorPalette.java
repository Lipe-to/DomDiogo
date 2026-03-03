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

    public static ColorPalette fromString(String text) {
        if (text == null) return BLUE;

        switch (text.toLowerCase()) {
            case "azul": return BLUE;
            case "vermelho": return RED;
            case "verde": return GREEN;
            case "laranja": return ORANGE;
            case "amarelo": return ORANGE; // Mapeado para o tom mais próximo disponível
            default:
                try {
                    // Tenta buscar pelo nome da constante (ex: "RED")
                    return ColorPalette.valueOf(text.toUpperCase());
                } catch (IllegalArgumentException e) {
                    return BLUE;
                }
        }
    }
}