package ru.clevertec.product.repository.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.utils.ProductTestData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryProductRepositoryTest {

    private final ProductRepository repository = new InMemoryProductRepository();
    private final Product product = ProductTestData.builder().build().buildProduct();

    @Test
    void findByIdShouldGiveProductOptional() {
//        given
        //        when
        Product actual = repository.findById(ProductTestData.UUID).orElseThrow();

//        then
        assertEquals(product, actual);
    }

    @Test
    void findByIdShouldGiveEmptyOptional() {
//        given
        //        when
        Optional<Product> actual = repository.findById(ProductTestData.UUID_THIRD);

//        then
        assertTrue(actual.isEmpty());
    }

    @Test
    void findAllShouldGiveListOfProducts() {
//        given
        List<Product> expected = List.of(product, ProductTestData.builder()
                .withUuid(ProductTestData.UUID_SECOND)
                .withName(ProductTestData.OTHER_NAME)
                .build().buildProduct());

//        when
        List<Product> actual = repository.findAll();

//        then
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void saveShouldSaveProductAndReturn() {
//        given
//        when
        Product actual = repository.save(product);

//        then
        assertEquals(product, actual);
    }

    @Test
    void saveShouldUpdateProductAndReturn() {
//        given
        Product expected = ProductTestData.builder()
                .withName(ProductTestData.OTHER_NAME)
                .build().buildProduct();

//        when
        Product actual = repository.save(expected);

//        then
        assertEquals(expected, actual);
    }

    @Test
    void saveThrowException() {
//        given
//        when
//        then
        assertThrows(IllegalArgumentException.class, () -> repository.save(null));
    }


    @ParameterizedTest
    @ValueSource(strings = {"e69ce31b-8266-409d-88ab-1141ebdb61e9",
            "1013b8f8-5291-490e-b506-83da820c67ed"})
    void deleteShouldDeleteByUuid(String str) {
        //        given
        UUID uuid = UUID.fromString(str);

        //        when
        repository.delete(uuid);

        //        then
        assertTrue(repository.findById(uuid).isEmpty());
    }
}
