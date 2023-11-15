package ru.clevertec.product.utils;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(setterPrefix = "with")
public class ProductTestData {

    public static final UUID UUID = java.util.UUID.fromString("e69ce31b-8266-409d-88ab-1141ebdb61e9");
    public static final String OTHER_NAME = "Other name";
    public static final UUID UUID_SECOND = java.util.UUID.fromString("1013b8f8-5291-490e-b506-83da820c67ed");
    public static final UUID UUID_THIRD = java.util.UUID.fromString("d850cf11-3241-4a9b-9055-e684117ee39b");

    @Builder.Default
    private UUID uuid = UUID;

    @Builder.Default
    private String name = "Same name";

    @Builder.Default
    private String description = "Same description";

    @Builder.Default
    private BigDecimal price = BigDecimal.TEN;

    @Builder.Default
    private LocalDateTime created = LocalDateTime.of(2023, 10, 10, 15, 15, 15);

    public Product buildProduct() {
        return new Product(uuid, name, description, price, created);
    }

    public InfoProductDto buildInfoProductDTO() {
        return new InfoProductDto(uuid, name, description, price);
    }

    public ProductDto buildProductDto() {
        return new ProductDto(name, description, price);
    }
}
