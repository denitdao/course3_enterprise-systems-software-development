package ua.kpi.tef.essd.entity;

import lombok.ToString;

@ToString
public enum OrderStatus {
    Pending("Pending"), Confirmed("Confirmed"), Shipped("Shipped"),
    Delivered("Delivered"), Cancelled("Cancelled");

    private final String title;

    OrderStatus(String title) {
        this.title = title;
    }
}
