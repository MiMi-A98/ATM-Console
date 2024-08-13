package MiMiA98.atm.service;

import MiMiA98.atm.dao.BankAccountDAO;
import MiMiA98.atm.entity.BankAccount;

import java.math.BigDecimal;

public abstract class BankAccountService {
    private final BankAccountDAO bankAccountDAO = new BankAccountDAO();

    protected BankAccountService() {
    }

    void validateAccountStatus(BankAccount account) {
        if (account.isFrozen()) {
            throw new IllegalStateException("Account is frozen!");
        }
        if (account.isClosed()) {
            throw new IllegalStateException("Account is closed!");
        }
    }

    public void deposit(BankAccount bankAccount, BigDecimal depositAmount) {
        if (bankAccount == null || depositAmount == null) {
            throw new IllegalArgumentException("Bank account or deposit amount is null");
        }

        if (depositAmount.compareTo(BigDecimal.ZERO) > 0) {

            BankAccount bankAccountActual = getBankAccount(bankAccount.getAccountNumber());
            if (bankAccountActual != null) {
                validateAccountStatus(bankAccountActual);
                doDeposit(bankAccount, depositAmount);
            } else {
                throw new NullPointerException("Bank account is null!");
            }
        } else {
            throw new IllegalArgumentException("Provided deposit amount is less than zero!");
        }
    }

    public void withdraw(BankAccount bankAccount, BigDecimal withdrawAmount) {
        if (bankAccount == null || withdrawAmount == null) {
            throw new IllegalArgumentException("Bank account or withdraw amount is null");
        }

        if (withdrawAmount.compareTo(BigDecimal.ZERO) > 0) {
            BankAccount bankAccountActual = getBankAccount(bankAccount.getAccountNumber());
            if (bankAccountActual != null && bankAccountActual.getBalance().compareTo(withdrawAmount) >= 0) {
                validateAccountStatus(bankAccountActual);
                doWithdraw(bankAccount, withdrawAmount);
            } else {
                throw new NullPointerException("Bank account is null!");
            }
        } else {
            throw new IllegalArgumentException("Provided withdraw amount is lower than zero or higher than balance!");
        }
    }

    public void transfer(BankAccount sourceAccount, BankAccount destinationAccount, BigDecimal transferAmount) {
        if (sourceAccount == null || destinationAccount == null || transferAmount == null) {
            throw new IllegalArgumentException("Source account, destination account or transfer amount is null");
        }

        if (sourceAccount.equals(destinationAccount)) {
            throw new IllegalArgumentException("Cannot transfer money into the same account!");
        }

        if (transferAmount.compareTo(BigDecimal.ZERO) > 0) {
            doTransfer(sourceAccount, destinationAccount, transferAmount);
        } else {
            throw new IllegalArgumentException("Invalid transfer amount: either less than zero or larger than the balance!");
        }
    }

    public void transferMoney(String sourceAccount, String destinationAccount, BigDecimal sourceBalance, BigDecimal destinationBalance) {
        bankAccountDAO.transferMoney(sourceAccount, destinationAccount, sourceBalance, destinationBalance);
    }

    public abstract BankAccount getBankAccount(String accountNumber);

    abstract void updateBalance(String accountNumber, BigDecimal newBalance);

    abstract void doDeposit(BankAccount bankAccount, BigDecimal depositAmount);

    abstract void doWithdraw(BankAccount bankAccount, BigDecimal withdrawAmount);

    abstract void doTransfer(BankAccount sourceAccount, BankAccount destinationAccount, BigDecimal transferAmount);

}

