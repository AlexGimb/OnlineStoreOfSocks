package com.example.onlinestoreofsocks.model;

public enum Size {
    XS(36),
    S(38),
    M(40),
    L(42),
    XL(44),
    XXL(46);
    private final int size;

    Size(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
