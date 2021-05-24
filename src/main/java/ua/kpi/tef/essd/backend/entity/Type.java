package ua.kpi.tef.essd.backend.entity;

import lombok.ToString;

/**
 * <code>[MAN, WOMAN, CHILDREN]</code>
 */
@ToString
public enum Type {
    MAN("Man"), WOMAN("Woman"), CHILDREN("Children");

    private final String title;

    Type(String title) {
        this.title = title;
    }
}
