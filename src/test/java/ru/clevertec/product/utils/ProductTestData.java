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

    @Builder.Default
    private UUID uuid = UUID;

    @Builder.Default
    private String NAME = "Same name";

    @Builder.Default
    private String DESCRIPTION = "Same description";

    @Builder.Default
    private BigDecimal PRICE = BigDecimal.TEN;

    @Builder.Default
    private LocalDateTime CREATED = LocalDateTime.of(2023, 10, 10, 15, 15, 15);

    public Product buildProduct() {
        return new Product(UUID, NAME, DESCRIPTION, PRICE, CREATED);
    }

    public InfoProductDto buildInfoProductDTO() {
        return new InfoProductDto(UUID, NAME, DESCRIPTION, PRICE);
    }

    public ProductDto buildProductDto() {
        return new ProductDto(NAME, DESCRIPTION, PRICE);
    }
}
