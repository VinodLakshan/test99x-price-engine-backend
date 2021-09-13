package com.project99x.priceengine.controller;

import com.project99x.priceengine.domain.Product;
import com.project99x.priceengine.dto.ProductPriceDetail;
import com.project99x.priceengine.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class PriceController {

    Logger logger = LoggerFactory.getLogger(PriceController.class);

    @Autowired
    private ProductService productService;

    /**
     * Get All Product Details
     * @return
     */
    @GetMapping()
    public ResponseEntity<List<Product>> getAllProductDetails(){
        logger.info("Product Controller accessed : get All Product Details");
        List<Product> allProductDetails = productService.getAllProductDetails();
        return new ResponseEntity<>(allProductDetails, HttpStatus.OK);
    }

    /**
     * Save a product into DB
     * @param product
     * @return
     */
    @PostMapping()
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        logger.info("Product Controller accessed : Save Product");
        Product savedProduct = productService.saveProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }

    /**
     * Calculate total price of a given product quantity
     * @return
     */
    @PostMapping("/calculatePrice")
    public ResponseEntity<ProductPriceDetail> getCalculatedProductDetails(@RequestBody ProductPriceDetail productPriceDetail){
        logger.info("Product Controller accessed : Get Calculated Multiple Product Details");
        ProductPriceDetail productDetails = productService.getCalculatedProductDetails(productPriceDetail);
        return new ResponseEntity<>(productDetails, HttpStatus.OK);
    }

    /**
     * Calculate total price of a list of products
     * @return
     */
    @PostMapping("/calculateMultipleProductPrice")
    public ResponseEntity<List<ProductPriceDetail>> getCalculatedMultipleProductDetails(@RequestBody List<ProductPriceDetail> productPriceDetails){
        logger.info("Product Controller accessed : Get Calculated Multiple Product Details");
        List<ProductPriceDetail> calculatedPriceList = new ArrayList<>();

        for (ProductPriceDetail detail:productPriceDetails) {
            calculatedPriceList.add(productService.getCalculatedProductDetails(detail));
        }
        return new ResponseEntity<>(calculatedPriceList, HttpStatus.OK);
    }
}
