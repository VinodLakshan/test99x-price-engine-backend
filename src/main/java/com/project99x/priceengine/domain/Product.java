package com.project99x.priceengine.domain;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productId;
    private String productName;
    private double unitPrice;
    private double cartonPrice;
    private int unitsPerCarton;
    private double cartonPriceIncrementPercentage;
    private double cartonDiscountPercentage;
    private int minimumCartonsToDiscount;

    public Product(){}

    public Product(long productId){
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getCartonPrice() {
        return cartonPrice;
    }

    public void setCartonPrice(double cartonPrice) {
        this.cartonPrice = cartonPrice;
    }

    public int getUnitsPerCarton() {
        return unitsPerCarton;
    }

    public void setUnitsPerCarton(int unitsPerCarton) {
        this.unitsPerCarton = unitsPerCarton;
    }

    public double getCartonPriceIncrementPercentage() {
        return cartonPriceIncrementPercentage;
    }

    public void setCartonPriceIncrementPercentage(double cartonPriceIncrementPercentage) {
        this.cartonPriceIncrementPercentage = cartonPriceIncrementPercentage;
    }

    public double getCartonDiscountPercentage() {
        return cartonDiscountPercentage;
    }

    public void setCartonDiscountPercentage(double cartonDiscountPercentage) {
        this.cartonDiscountPercentage = cartonDiscountPercentage;
    }

    public int getMinimumCartonsToDiscount() {
        return minimumCartonsToDiscount;
    }

    public void setMinimumCartonsToDiscount(int minimumCartonsToDiscount) {
        this.minimumCartonsToDiscount = minimumCartonsToDiscount;
    }
}
