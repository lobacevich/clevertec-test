package ru.clevertec.product.repository.impl;

import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryProductRepository implements ProductRepository {

    private List<Product> products = new ArrayList<>();

    public InMemoryProductRepository() {
        products.add(new Product(UUID.fromString("e69ce31b-8266-409d-88ab-1141ebdb61e9"),
                "Same name",
                "Same description",
                BigDecimal.TEN,
                LocalDateTime.of(2023, 10, 10, 15, 15, 15)));
        products.add(new Product(UUID.fromString("1013b8f8-5291-490e-b506-83da820c67ed"),
                "Other name",
                "Same description",
                BigDecimal.TEN,
                LocalDateTime.of(2023, 10, 10, 15, 15, 15)));
    }

    @Override
    public Optional<Product> findById(UUID uuid) {
        return products.stream()
                .filter(pr -> pr.getUuid().equals(uuid))
                .findFirst();
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Product save(Product product) {
        if (product == null) {
            throw new IllegalArgumentException();
        }
        products.add(product);
        return product;
    }

    @Override
    public void delete(UUID uuid) {
        products = products.stream()
                .filter(pr -> !pr.getUuid().equals(uuid))
                .collect(Collectors.toList());
    }
}
