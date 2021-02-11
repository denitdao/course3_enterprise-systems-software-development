package ua.kpi.tef.essd.entity;

/**
 * <code>[MAN, WOMAN, CHILDREN]</code>
 */
public enum Type {
    MAN("Man"), WOMAN("Woman"), CHILDREN("Children");

    private final String title;

    Type(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
