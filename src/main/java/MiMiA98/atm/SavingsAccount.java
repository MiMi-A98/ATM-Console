package MiMiA98.atm;

import java.math.BigDecimal;

public class SavingsAccount extends DepositAccount implements Transactional {

    private static final double INTEREST_RATE = 0.02;

    public SavingsAccount(int accountCode, String accountNumber, String currency, UserAccount userAccount) {
        super(accountCode, accountNumber, currency, 0.0, INTEREST_RATE, userAccount);
    }

    @Override
    public String toString() {
        return "Savings account" + "\n" +
                "User name: " + getUserName() + "\n" +
                "Account number: " + getAccountNumber() + "\n" +
                "Currency: " + getCurrency() + "\n" +
                "Balance: " + getBalance() + "\n" +
                "Interest rate: " + INTEREST_RATE + "\n";
    }

    @Override
    public void deposit(BigDecimal depositAmount) {
        if (isClosed()) {
            throw new IllegalStateException("Account is closed!");
        }

        if (depositAmount.compareTo(BigDecimal.valueOf(0)) > 0) {
            setBalance(getBalance().add(depositAmount));
        } else {
            throw new IllegalArgumentException("Provided deposit amount is less than zero!");
        }
    }

    @Override
    public void withdraw(BigDecimal withdrawAmount) {
        if (isFrozen()) {
            throw new IllegalStateException("Account is frozen!");
        }
        if (isClosed()) {
            throw new IllegalStateException("Account is closed!");
        }

        if ((getBalance().compareTo(withdrawAmount)) >= 0 && (withdrawAmount.compareTo(BigDecimal.valueOf(0)) > 0)) {
            setBalance(getBalance().subtract(withdrawAmount));
        } else {
            throw new IllegalArgumentException("Provided withdraw amount is larger than balance!");
        }
    }

    @Override
    public void transfer(BigDecimal transferAmount, BankAccount destinationBankAccount) {
        if (!destinationBankAccount.getUserAccount().equals(getUserAccount())) {
            throw new IllegalArgumentException("Destination account is not associated to same user account!");
        }
        super.transfer(transferAmount, destinationBankAccount);
    }
}
