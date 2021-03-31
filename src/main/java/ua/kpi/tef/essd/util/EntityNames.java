package ua.kpi.tef.essd.util;

import lombok.ToString;

@ToString
public enum EntityNames {

    USER("User"), CLOTHING("Clothing"), CLOTHES_SET("Clothes set"),
    ORDER("Order"), PART("Part"), PROPERTY("Property");

    private final String title;

    EntityNames(String title) {
        this.title = title;
    }

}
