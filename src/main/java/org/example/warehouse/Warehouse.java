package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {

    private static Warehouse instance;
    private final String name;
    private final Map<UUID, ProductRecord> products;
    private final List<ProductRecord> changedProducts; // Ändrad till List för att behålla ordning

    // Standard konstruktor
    private Warehouse() {
        this.name = "MyStore";
        this.products = new LinkedHashMap<>();
        this.changedProducts = new ArrayList<>(); // Använd ArrayList för att behålla ändrade produkter i ordning
    }

    // Överlagrad konstruktor för namn
    private Warehouse(String name) {
        this.name = name;
        this.products = new LinkedHashMap<>();
        this.changedProducts = new ArrayList<>();
    }

    // Singleton instansmetod
    public static Warehouse getInstance() {
        return new Warehouse();
    }

    // Överlagrad metod för namn
    public static Warehouse getInstance(String name) {
        if (instance == null || !instance.isEmpty()) {
            instance = new Warehouse(name);
        }
        return instance;
    }

    // Kontrollera om lagret är tomt
    public boolean isEmpty() {
        return products.isEmpty();
    }

    // Hämta alla produkter
    public List<ProductRecord> getProducts() {
        return List.copyOf(products.values());
    }

    // Hämta en produkt med dess UUID
    public Optional<ProductRecord> getProductById(UUID uuid) {
        return Optional.ofNullable(products.get(uuid));
    }

    // Lägg till en ny produkt
    public ProductRecord addProduct(UUID uuid, String name, Category category, BigDecimal price) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category can't be null.");
        }
        if (uuid == null) {
            uuid = UUID.randomUUID(); // Generera UUID om det inte anges
        }
        if (price == null) {
            price = BigDecimal.ZERO; // Sätt default pris om det är null
        }
        if (products.containsKey(uuid)) {
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }
        ProductRecord product = new ProductRecord(uuid, name, category, price);
        products.put(uuid, product);
        return product;
    }

    // Uppdatera produktens pris
    public void updateProductPrice(UUID uuid, BigDecimal newPrice) {
        if (!products.containsKey(uuid)) {
            throw new IllegalArgumentException("Product with that id doesn't exist.");
        }

        ProductRecord product = products.get(uuid);
        product.setPrice(newPrice);

        // Lägg till produkt i ändrade produkter om den inte redan finns
        if (!changedProducts.contains(product)) {
            changedProducts.add(product);
        }
    }

    // Hämta ändrade produkter
    public List<ProductRecord> getChangedProducts() {
        return new ArrayList<>(changedProducts); // Returnera kopia av ändrade produkter
    }

    // Hämta produkter grupperade efter kategori
    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        return products.values().stream()
                .collect(Collectors.groupingBy(ProductRecord::category));
    }

    // Hämta produkter efter kategori
    public List<ProductRecord> getProductsBy(Category category) {
        return products.values().stream()
                .filter(product -> product.category().equals(category))
                .collect(Collectors.toList());
    }
}
