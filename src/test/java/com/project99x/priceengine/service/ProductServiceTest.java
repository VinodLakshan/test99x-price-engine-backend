package com.project99x.priceengine.service;

import com.project99x.priceengine.domain.Product;
import com.project99x.priceengine.dto.ProductPriceDetail;
import com.project99x.priceengine.exception.DBException;
import com.project99x.priceengine.exception.ProductNotFoundException;
import com.project99x.priceengine.repository.ProductRepository;
import com.project99x.priceengine.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepositoryMock;

    @DisplayName("Test Get All Product Details")
    @Test
    public void testGetAllProductDetails() {
        List<Product> testProductList = this.getTestProductList();
        Mockito.when(productRepositoryMock.findAll()).thenReturn(testProductList);
        List<Product> actualProductList = productService.getAllProductDetails();

        assertEquals(testProductList, actualProductList);
        Mockito.verify(productRepositoryMock, Mockito.atLeastOnce()).findAll();
    }

    @DisplayName("Test Get All Product Details when DB Exception occurs")
    @Test
    public void testGetAllProductDetails_whenDBException() {
        Mockito.when(productRepositoryMock.findAll()).thenThrow(DBException.class);
        assertThrows(DBException.class, () -> {
            productService.getAllProductDetails();
        });
        Mockito.verify(productRepositoryMock, Mockito.atLeastOnce()).findAll();
    }

    @DisplayName("Test Save Product")
    @Test
    public void testSaveProduct() {
        Product testProduct = this.getTestProduct();
        Mockito.when(productRepositoryMock.save(testProduct)).thenReturn(testProduct);
        Product actualProduct = productService.saveProduct(testProduct);

        assertEquals(testProduct, actualProduct);
        Mockito.verify(productRepositoryMock, Mockito.atLeastOnce()).save(testProduct);
    }

    @DisplayName("Test Save Product when DB Exception occurs")
    @Test
    public void testSaveProduct_WhenDBException() {
        Product testProduct = this.getTestProduct();
        Mockito.when(productRepositoryMock.save(testProduct)).thenThrow(DBException.class);
        assertThrows(DBException.class, () -> {
            productService.saveProduct(testProduct);
        });
        Mockito.verify(productRepositoryMock, Mockito.atLeastOnce()).save(testProduct);
    }

    @DisplayName("Test Calculate price when quantity type is in cartons")
    @Test
    public void testCalculatePrice_whenQuantityTypeIsInCartons() {
        Product testProduct = this.getTestProduct();
        ProductPriceDetail testProductPriceDetail = this.getTestProductPriceDetail();

        double expectedTotalPrice = testProduct.getCartonPrice() * testProductPriceDetail.getQuantity();
        Mockito.when(productRepositoryMock.findById(testProductPriceDetail.getProductId())).thenReturn(Optional.of(testProduct));
        ProductPriceDetail calculatedProductDetails = productService.getCalculatedProductDetails(testProductPriceDetail);
        assertEquals(expectedTotalPrice, calculatedProductDetails.getTotalPrice());
        Mockito.verify(productRepositoryMock, Mockito.atLeastOnce()).findById(testProductPriceDetail.getProductId());
    }

    @DisplayName("Test Calculate price when quantity type is in cartons and eligible for discount")
    @Test
    public void testCalculatePrice_whenQuantityTypeIsInCartonsAndEligibleForDiscount() {
        Product testProduct = this.getTestProduct();
        ProductPriceDetail testProductPriceDetail = this.getTestProductPriceDetail();
        testProductPriceDetail.setQuantity(5);

        double expectedTotalPrice = (testProduct.getCartonPrice() * (100 - testProduct.getCartonDiscountPercentage())) / 100 * testProductPriceDetail.getQuantity();
        Mockito.when(productRepositoryMock.findById(testProductPriceDetail.getProductId())).thenReturn(Optional.of(testProduct));
        ProductPriceDetail calculatedProductDetails = productService.getCalculatedProductDetails(testProductPriceDetail);
        assertEquals(expectedTotalPrice, calculatedProductDetails.getTotalPrice());
        Mockito.verify(productRepositoryMock, Mockito.atLeastOnce()).findById(testProductPriceDetail.getProductId());
    }

    @DisplayName("Test Calculate price when quantity type is in units")
    @Test
    public void testCalculatePrice_whenQuantityTypeIsInUnits() {
        Product testProduct = this.getTestProduct();
        ProductPriceDetail testProductPriceDetail = this.getTestProductPriceDetail();
        testProductPriceDetail.setQuantityType("units");

        double expectedTotalPrice = testProduct.getUnitPrice() * testProductPriceDetail.getQuantity();
        Mockito.when(productRepositoryMock.findById(testProductPriceDetail.getProductId())).thenReturn(Optional.of(testProduct));
        ProductPriceDetail calculatedProductDetails = productService.getCalculatedProductDetails(testProductPriceDetail);
        assertEquals(expectedTotalPrice, calculatedProductDetails.getTotalPrice());
        Mockito.verify(productRepositoryMock, Mockito.atLeastOnce()).findById(testProductPriceDetail.getProductId());
    }

    @DisplayName("Test Calculate price when quantity type is in units and more than a Carton")
    @Test
    public void testCalculatePrice_whenQuantityTypeIsInUnitsAndMoreThanACarton() {
        Product testProduct = this.getTestProduct();
        ProductPriceDetail testProductPriceDetail = this.getTestProductPriceDetail();
        testProductPriceDetail.setQuantityType("units");
        testProductPriceDetail.setQuantity(12);

        int cartons = testProductPriceDetail.getQuantity() / testProduct.getUnitsPerCarton();
        int units = testProductPriceDetail.getQuantity() % testProduct.getUnitsPerCarton();
        double expectedTotalPrice = testProduct.getCartonPrice() * cartons +
                testProduct.getUnitPrice() * units;
        Mockito.when(productRepositoryMock.findById(testProductPriceDetail.getProductId())).thenReturn(Optional.of(testProduct));
        ProductPriceDetail calculatedProductDetails = productService.getCalculatedProductDetails(testProductPriceDetail);
        assertEquals(expectedTotalPrice, calculatedProductDetails.getTotalPrice());
        Mockito.verify(productRepositoryMock, Mockito.atLeastOnce()).findById(testProductPriceDetail.getProductId());
    }

    @DisplayName("Test Calculate price when quantity type is in units and Eligible for discount")
    @Test
    public void testCalculatePrice_whenQuantityTypeIsInUnitsAndEligibleForDiscount() {
        Product testProduct = this.getTestProduct();
        ProductPriceDetail testProductPriceDetail = this.getTestProductPriceDetail();
        testProductPriceDetail.setQuantityType("units");
        testProductPriceDetail.setQuantity(16);

        int cartons = testProductPriceDetail.getQuantity() / testProduct.getUnitsPerCarton();
        int units = testProductPriceDetail.getQuantity() % testProduct.getUnitsPerCarton();
        double expectedTotalPrice = testProduct.getCartonPrice() * (100-testProduct.getCartonDiscountPercentage())/100 * cartons +
                testProduct.getUnitPrice() * units;
        Mockito.when(productRepositoryMock.findById(testProductPriceDetail.getProductId())).thenReturn(Optional.of(testProduct));
        ProductPriceDetail calculatedProductDetails = productService.getCalculatedProductDetails(testProductPriceDetail);
        assertEquals(expectedTotalPrice, calculatedProductDetails.getTotalPrice());
        Mockito.verify(productRepositoryMock, Mockito.atLeastOnce()).findById(testProductPriceDetail.getProductId());
    }

    @DisplayName("Test Calculate price when DBException occurs")
    @Test
    public void testCalculatePrice_whenDBException() {

        ProductPriceDetail testProductPriceDetail = this.getTestProductPriceDetail();
        Mockito.when(productRepositoryMock.findById(testProductPriceDetail.getProductId())).thenThrow(DBException.class);
        assertThrows(DBException.class, () -> {
            productService.getCalculatedProductDetails(testProductPriceDetail);
        });
        Mockito.verify(productRepositoryMock, Mockito.atLeastOnce()).findById(testProductPriceDetail.getProductId());
    }

    @DisplayName("Test Calculate price when ProductNotFoundException occurs")
    @Test
    public void testCalculatePrice_whenProductNotFoundException() {

        ProductPriceDetail testProductPriceDetail = this.getTestProductPriceDetail();
        Mockito.when(productRepositoryMock.findById(testProductPriceDetail.getProductId())).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> {
            productService.getCalculatedProductDetails(testProductPriceDetail);
        });
        Mockito.verify(productRepositoryMock, Mockito.atLeastOnce()).findById(testProductPriceDetail.getProductId());
    }

    /**
     * Get a list of test products
     * @return
     */
    private List<Product> getTestProductList() {
        List<Product> products = new ArrayList<>();
        products.add(getTestProduct());
        return products;
    }

    /**
     * Get a test Product object
     * @return
     */
    private Product getTestProduct() {
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Penguin-ear");
        product.setCartonPrice(175);
        product.setUnitsPerCarton(5);
        product.setUnitPrice(11.38);
        product.setCartonDiscountPercentage(10);
        product.setCartonPriceIncrementPercentage(30);
        product.setMinimumCartonsToDiscount(3);

        return product;
    }

    /**
     * Get a test ProductPriceDetail object
     * @return
     */
    private ProductPriceDetail getTestProductPriceDetail() {
        ProductPriceDetail productPriceDetail = new ProductPriceDetail();
        productPriceDetail.setProductId(1);
        productPriceDetail.setQuantityType("cartons");
        productPriceDetail.setQuantity(2);
        return productPriceDetail;
    }

}