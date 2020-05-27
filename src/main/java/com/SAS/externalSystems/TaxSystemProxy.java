package com.SAS.externalSystems;

public class TaxSystemProxy implements ITaxSystem {

    @Override
    public double getTaxRate(double revenueAmount) {
        TaxSystem taxSystem = new TaxSystem();
        return taxSystem.getTaxRate(revenueAmount);
    }
}
