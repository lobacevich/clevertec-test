package ru.clevertec.product.mapper.impl;

import org.junit.jupiter.api.Test;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.utils.ProductTestData;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductMapperImplTest {

    private final ProductMapper mapper = new ProductMapperImpl();
    private final Product product = ProductTestData.builder().build().buildProduct();

    @Test
    void toProductShouldMapDTOToProduct() {
//        given
        ProductDto dto = ProductTestData.builder().build().buildProductDto();
        Product expected = ProductTestData.builder()
                .withUuid(null)
                .withCreated(LocalDateTime.now())
                .build().buildProduct();

//        when
        Product actual = mapper.toProduct(dto);

//        then
        assertThat(actual)
                .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice());
    }

    @Test
    void toInfoProductDtoShouldMapProductToInfoProductDto() {
        //        given
        InfoProductDto expected = ProductTestData.builder().build().buildInfoProductDTO();

        //        when
        InfoProductDto actual = mapper.toInfoProductDto(product);

        //        then
        assertEquals(expected, actual);
    }

    @Test
    void mergeShouldMergeDtoIntoProduct() {
//        given
        Product expected = ProductTestData.builder()
                .withName(ProductTestData.OTHER_NAME)
                .build().buildProduct();
        ProductDto dto = ProductTestData.builder()
                .withName(ProductTestData.OTHER_NAME)
                .build().buildProductDto();

//        when
        Product actual = mapper.merge(product, dto);

//        then
        assertEquals(expected, actual);
    }
}
