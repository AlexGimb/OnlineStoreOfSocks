package com.example.onlinestoreofsocks.model;

public enum Color {
    BLACK("Черные"),
    WHITE("Белые"),
    RED("Красные"),
    GREEN("Зеленые"),
    YELLOW("Желтые"),
    BLUE("Синие");

    private final String color;

    Color(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
