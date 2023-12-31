package ru.clevertec.product.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ProductNotFoundException;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.utils.ProductTestData;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductMapper mapper;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Captor
    private ArgumentCaptor<Product> captor;
    private final Product product = ProductTestData.builder().build().buildProduct();
    private final InfoProductDto infoProductDto = ProductTestData.builder().build().buildInfoProductDTO();
    private final ProductDto productDto = ProductTestData.builder().build().buildProductDto();

    @Test
    void getShouldReturnInfoProductDtoByUuid() {
//        given
        when(productRepository.findById(ProductTestData.UUID))
                .thenReturn(Optional.of(product));
        when(mapper.toInfoProductDto(product))
                .thenReturn(infoProductDto);

//        when
        InfoProductDto actual = productService.get(ProductTestData.UUID);

//        then
        assertEquals(infoProductDto, actual);
    }

    @Test
    void getShouldThrowException() {
//        given
        when(productRepository.findById(ProductTestData.UUID))
                .thenReturn(Optional.empty());

//        when
//        then
        assertThrows(ProductNotFoundException.class, () -> productService.get(ProductTestData.UUID));
    }

    @Test
    void getAllShouldGetAllInfoProductDto() {
        //        given
        Product otherProduct = ProductTestData.builder()
                .withUuid(ProductTestData.UUID_SECOND)
                .withName(ProductTestData.OTHER_NAME)
                .build().buildProduct();
        InfoProductDto otherInfoProductDto = ProductTestData.builder()
                .withName(ProductTestData.OTHER_NAME)
                .build().buildInfoProductDTO();
        List<InfoProductDto> expected = List.of(infoProductDto, otherInfoProductDto);
        when(productRepository.findAll())
                .thenReturn(List.of(product, otherProduct));
        when(mapper.toInfoProductDto(product))
                .thenReturn(infoProductDto);
        when(mapper.toInfoProductDto(otherProduct))
                .thenReturn(otherInfoProductDto);

//        when
        List<InfoProductDto> actual = productService.getAll();

//        then
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void createShouldSaveProductFromProductDTOReturnUuid() {
        //        given
        when(mapper.toProduct(productDto))
                .thenReturn(product);
        when(productRepository.save(product))
                .thenReturn(product);

//        when
        productService.create(productDto);

//        then
        verify(productRepository).save(captor.capture());

        assertEquals(productDto.name(), captor.getValue().getName());
        assertEquals(productDto.description(), captor.getValue().getDescription());
        assertEquals(productDto.price(), captor.getValue().getPrice());
    }

    @Test
    void updateShouldUpdateProductWithUuidFromProductDTO() {
        //        given
        Product otherProduct = ProductTestData.builder()
                .withName(ProductTestData.OTHER_NAME)
                .build().buildProduct();
        ProductDto otherProductDto = ProductTestData.builder()
                .withName(ProductTestData.OTHER_NAME)
                .build().buildProductDto();
        when(productRepository.findById(ProductTestData.UUID))
                .thenReturn(Optional.of(product));
        when(mapper.merge(product, otherProductDto))
                .thenReturn(otherProduct);
        when(productRepository.save(otherProduct))
                .thenReturn(otherProduct);

//        when
        productService.update(ProductTestData.UUID, otherProductDto);

//        then
        verify(productRepository).save(captor.capture());

        assertEquals(ProductTestData.UUID, captor.getValue().getUuid());
        assertEquals(otherProductDto.name(), captor.getValue().getName());
        assertEquals(otherProductDto.description(), captor.getValue().getDescription());
        assertEquals(otherProductDto.price(), captor.getValue().getPrice());
    }

    @Test
    void deleteShouldDeleteProductByUuid() {
        //        given
//        when
        productService.delete(ProductTestData.UUID);

//        then
        verify(productRepository).delete(ProductTestData.UUID);
    }
}
