package org.example.warehouse;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class ProductRecord {
    private final UUID uuid;
    private final String name;
    private final Category category;
    private BigDecimal price;

    public ProductRecord(UUID uuid, String name, Category category, BigDecimal price) {
        this.uuid = uuid != null ? uuid : UUID.randomUUID();
        this.name = name;
        this.category = category;
        this.price = price != null ? price : BigDecimal.ZERO;
    }

    public UUID uuid() {
        return uuid;
    }

    public String name() {
        return name;
    }


    public Category category() {
        return category;
    }

    public BigDecimal price() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price != null ? price : BigDecimal.ZERO;
    }

}
