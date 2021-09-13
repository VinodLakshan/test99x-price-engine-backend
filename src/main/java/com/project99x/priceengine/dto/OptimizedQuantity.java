package com.project99x.priceengine.dto;

public class OptimizedQuantity {

    private int optimizedCartonCount;
    private int optimizeUnitCount;

    public OptimizedQuantity() {
    }

    public OptimizedQuantity(int optimizedCartonCount, int optimizeUnitCount) {
        this.optimizedCartonCount = optimizedCartonCount;
        this.optimizeUnitCount = optimizeUnitCount;
    }

    public int getOptimizedCartonCount() {
        return optimizedCartonCount;
    }

    public void setOptimizedCartonCount(int optimizedCartonCount) {
        this.optimizedCartonCount = optimizedCartonCount;
    }

    public int getOptimizeUnitCount() {
        return optimizeUnitCount;
    }

    public void setOptimizeUnitCount(int optimizeUnitCount) {
        this.optimizeUnitCount = optimizeUnitCount;
    }
}
