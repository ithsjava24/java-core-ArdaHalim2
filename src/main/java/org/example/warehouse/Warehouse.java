package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;

public class Warehouse {
    private static Warehouse instance;
    private final String name;
    private final Map<UUID, ProductRecord> products;
    private final Set<ProductRecord> changedProducts;

    private Warehouse() {
        this.name = "MyStore";
        this.products = new HashMap<>();
        this.changedProducts = new HashSet<>();
    }

    private Warehouse(String name) {
        this.name = name;
        this.products = new HashMap<>();
        this.changedProducts = new HashSet<>();
    }

    public static Warehouse getInstance() {
        if (instance == null || !instance.isEmpty()) {
            instance = new Warehouse();
        }
        return instance;
    }

    public static Warehouse getInstance(String name) {
        if (instance == null || !instance.isEmpty()) {
            instance = new Warehouse(name);
        }
        return instance;
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public List<ProductRecord> getProducts() {
        return List.copyOf(products.values());
    }

    public Optional<ProductRecord> getProductById(UUID uuid) {
        return Optional.ofNullable(products.get(uuid));
    }

    public ProductRecord addProduct(UUID uuid, String name, Category category, BigDecimal price) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category can't be null.");
        }
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        if (price == null) {
            price = BigDecimal.ZERO;
        }
        if (products.containsKey(uuid)) {
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }
        ProductRecord product = new ProductRecord(uuid, name, category, price);
        products.put(uuid, product);
        return product;
    }

    public void updateProductPrice(UUID uuid, BigDecimal newPrice) {
        if (!products.containsKey(uuid)) {
            throw new IllegalArgumentException("Product with that id doesn't exist.");
        }

        ProductRecord product = products.get(uuid);
        product.setPrice(newPrice);
        changedProducts.add(product);
    }

    public List<ProductRecord> getChangedProducts() {
        return new ArrayList<>(changedProducts);
    }

    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        Map<Category, List<ProductRecord>> categoryMap = new HashMap<>();
        for (ProductRecord product : products.values()) {
            categoryMap.computeIfAbsent(product.category(), k -> new ArrayList<>()).add(product);
        }
        return categoryMap;
    }

    public List<ProductRecord> getProductsBy(Category category) {
        List<ProductRecord> productsInCategory = new ArrayList<>();
        for (ProductRecord product : products.values()) {
            if (product.category().equals(category)) {
                productsInCategory.add(product);
            }
        }
        return productsInCategory;
    }
}
