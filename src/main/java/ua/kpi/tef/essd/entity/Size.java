package ua.kpi.tef.essd.entity;

/**
 * <code>[XS, S, M, L, XL]</code>
 */
public enum Size {
    XS("XS"), S("S"), M("M"), L("L"), XL("XL");

    private final String title;

    Size(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
