package com.SAS.externalSystems;

public class AccountSystemProxy implements IAccountSystem {

    @Override
    public boolean addPayment(String teamName, String date, double amount) {
        AccountSystem accountSystem = new AccountSystem();
        return accountSystem.addPayment(teamName, date, amount);
    }
}
