package com.project99x.priceengine.service.impl;

import com.project99x.priceengine.domain.Product;
import com.project99x.priceengine.dto.OptimizedQuantity;
import com.project99x.priceengine.dto.ProductPriceDetail;
import com.project99x.priceengine.exception.DBException;
import com.project99x.priceengine.exception.ProductNotFoundException;
import com.project99x.priceengine.repository.ProductRepository;
import com.project99x.priceengine.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    /**
     * Save a product into DB
     *
     * @param product
     * @return
     */
    @Override
    public Product saveProduct(Product product) {

        logger.info("Product Service accessed : Save Product");
        try {
            product.setUnitPrice(calculateUnitPrice(product));
            return productRepository.save(product);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new DBException("Database Error");
        }
    }

    /**
     * Get All Product Details
     *
     * @return
     */
    @Override
    public List<Product> getAllProductDetails() {

        logger.info("Product Service accessed : get All Product Details");
        try {
            return productRepository.findAll();

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new DBException("Database Error");
        }
    }

    /**
     * Calculate total price of a given product quantity
     *
     * @param productPriceDetail
     * @return
     */
    @Override
    public ProductPriceDetail getCalculatedProductDetails(ProductPriceDetail productPriceDetail) {

        logger.info("Product Service accessed : get Calculated Product Details");
        Product product = this.getProductDetailsById(new Product(productPriceDetail.getProductId()));

        if (product != null) {
            productPriceDetail.setProductName(product.getProductName());
            OptimizedQuantity optimizedQuantity = this.optimizeQuantity(product, productPriceDetail);
            double totalPrice = 0;

            if (optimizedQuantity.getOptimizedCartonCount() >= product.getMinimumCartonsToDiscount()) {
                totalPrice = ((product.getCartonPrice() * (100 - product.getCartonDiscountPercentage())) / 100)
                        * optimizedQuantity.getOptimizedCartonCount();

            } else if (optimizedQuantity.getOptimizedCartonCount() > 0) {
                totalPrice = product.getCartonPrice() * optimizedQuantity.getOptimizedCartonCount();
            }

            totalPrice += (product.getUnitPrice() * optimizedQuantity.getOptimizeUnitCount());

            productPriceDetail.setTotalPrice(totalPrice);
            return productPriceDetail;

        } else {
            logger.error("Product Not Found");
            throw new ProductNotFoundException("Product not found");
        }
    }

    /**
     * Optimize Product Quantity
     *
     * @param product
     * @param productPriceDetail
     * @return
     */
    private OptimizedQuantity optimizeQuantity(Product product, ProductPriceDetail productPriceDetail) {

        logger.info("Product Service accessed : Optimize Quantity");
        OptimizedQuantity optimizedQuantity = new OptimizedQuantity();
        if (productPriceDetail.getQuantityType().equalsIgnoreCase("cartons")) {
            optimizedQuantity.setOptimizedCartonCount(productPriceDetail.getQuantity());

        } else {
            int optimizedCartons = productPriceDetail.getQuantity() / product.getUnitsPerCarton();
            optimizedQuantity.setOptimizedCartonCount(optimizedCartons);

            int optimizeUnits = productPriceDetail.getQuantity() % product.getUnitsPerCarton();
            optimizedQuantity.setOptimizeUnitCount(optimizeUnits);
        }
        return optimizedQuantity;
    }

    /**
     * Get Product Details by ID
     *
     * @param product
     * @return
     */
    private Product getProductDetailsById(Product product) {

        logger.info("Product Service accessed : get Product Details by ID");
        try {
            Optional<Product> foundProduct = productRepository.findById(product.getProductId());
            if (foundProduct.isEmpty()) {
                return null;
            }
            return foundProduct.get();

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new DBException("Database Error");
        }
    }

    /**
     * calculate unit price of a product
     *
     * @param product
     * @return
     */
    private double calculateUnitPrice(Product product) {

        logger.info("Product Service accessed : Calculate Unit Price");
        double unitPrice = 0;
        double incrementedCartonPrice = (product.getCartonPrice() * product.getCartonPriceIncrementPercentage() / 100) + product.getCartonPrice();
        unitPrice = incrementedCartonPrice / product.getUnitsPerCarton();
        BigDecimal bigDecimal = new BigDecimal(unitPrice).setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
