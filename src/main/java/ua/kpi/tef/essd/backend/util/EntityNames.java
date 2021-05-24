package ua.kpi.tef.essd.backend.util;

public enum EntityNames {

    USER("User"), CLOTHING("Clothing"), CLOTHES_SET("Clothes set"),
    ORDER("Order"), PART("Part"), PROPERTY("Property");

    private final String title;

    EntityNames(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
