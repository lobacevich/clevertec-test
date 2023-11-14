package ru.clevertec.product.repository.impl;

import org.junit.jupiter.api.Test;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.utils.ProductTestData;

import java.util.List;
import java.util.Optional;

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
                .withNAME(ProductTestData.OTHER_NAME)
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
                .withNAME(ProductTestData.OTHER_NAME)
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


    @Test
    void deleteShouldDeleteByUuid() {
        //        given
        //        when
        repository.delete(ProductTestData.UUID);

        //        then
        assertTrue(repository.findById(ProductTestData.UUID).isEmpty());
    }
}
