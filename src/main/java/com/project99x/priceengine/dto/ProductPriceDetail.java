package com.project99x.priceengine.dto;

public class ProductPriceDetail {

    private long productId;
    private String productName;
    private String quantityType;
    private int quantity;
    private double totalPrice;

    public ProductPriceDetail(){}

    public ProductPriceDetail(long productId, String productName, String quantityType, int quantity, double totalPrice) {
        this.productId = productId;
        this.productName = productName;
        this.quantityType = quantityType;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
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

    public String getQuantityType() {
        return quantityType;
    }

    public void setQuantityType(String quantityType) {
        this.quantityType = quantityType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
