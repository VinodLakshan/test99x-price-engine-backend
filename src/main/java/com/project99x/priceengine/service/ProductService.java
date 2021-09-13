package com.project99x.priceengine.service;

import com.project99x.priceengine.domain.Product;
import com.project99x.priceengine.dto.ProductPriceDetail;

import java.util.List;

public interface ProductService {

    Product saveProduct(Product product);
    List<Product> getAllProductDetails();
    ProductPriceDetail getCalculatedProductDetails(ProductPriceDetail productPriceDetail);
}
