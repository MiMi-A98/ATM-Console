package MiMiA98.atm.service;

import MiMiA98.atm.entity.BankAccount;
import MiMiA98.atm.entity.CheckingAccount;
import MiMiA98.atm.entity.FixedTermAccount;
import MiMiA98.atm.entity.SavingsAccount;

import java.util.HashMap;
import java.util.Map;

public class BankAccountServiceLocator {

    private static final Map<String, BankAccountService> serviceCache = new HashMap<>();

    public static BankAccountService getService(BankAccount bankAccount) {
        BankAccountService service = null;

        switch (bankAccount) {
            case CheckingAccount checkingAccount ->
                    service = serviceCache.computeIfAbsent(bankAccount.getClass().getName(),
                            k -> new CheckingAccountService());
            case SavingsAccount savingsAccount ->
                    service = serviceCache.computeIfAbsent(bankAccount.getClass().getName(),
                            k -> new SavingsService());
            case FixedTermAccount fixedTermAccount ->
                    service = serviceCache.computeIfAbsent(bankAccount.getClass().getName(),
                            k -> new FixedTermService());
            case null, default -> throw new IllegalArgumentException("Bank account is null or type is not supported.");
        }

        return service;
    }
}
