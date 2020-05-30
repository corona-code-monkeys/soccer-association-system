package com.SAS.externalSystems;

public class TaxSystem implements ITaxSystem {
    @Override
    public double getTaxRate(double revenueAmount) {
        return revenueAmount * 0.17;
    }
}
