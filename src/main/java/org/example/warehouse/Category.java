package org.example.warehouse;

import java.util.HashMap;
import java.util.Map;

public class Category {
    private final String name;
    private static final Map<String, Category> instances = new HashMap<>();

    private Category(String name) {
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    // Statisk metod för att skapa eller hämta en kategori
    public static Category of(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Category name can't be null");
        }
        return instances.computeIfAbsent(name, Category::new);
    }

    public String getName() {
        return name;
    }
}
