package ua.kpi.tef.essd.backend.entity;

import lombok.ToString;

/**
 * <code>[XS, S, M, L, XL]</code>
 */
@ToString
public enum Size {
    XS("XS"), S("S"), M("M"), L("L"), XL("XL");

    private final String title;

    Size(String title) {
        this.title = title;
    }
}
