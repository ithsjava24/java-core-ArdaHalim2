package org.example.warehouse;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class ProductRecord {
    private final UUID uuid;
    private final String name;
    private final Category category;
    private BigDecimal price;

    // Konstruktor för att skapa en produktpost med specifika egenskaper
    public ProductRecord(UUID uuid, String name, Category category, BigDecimal price) {
        this.uuid = uuid;
        this.name = name;
        this.category = category;
        this.price = price;
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

    // Overrida equals-metoden för att jämföra produktposter
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductRecord that)) return false;
        return uuid.equals(that.uuid);
    }

    // Overrida hashCode-metoden för att generera en hashkod baserat på uuid
    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
