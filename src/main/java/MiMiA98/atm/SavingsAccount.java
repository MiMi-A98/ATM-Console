package MiMiA98.atm;

import java.math.BigDecimal;

public class SavingsAccount extends DepositAccount {

    private static final double INTEREST_RATE = 0.02;

    public SavingsAccount(String accountNumber, String currency, UserAccount userAccount) {
        super(accountNumber, currency, 0.0, INTEREST_RATE, userAccount);
    }

    @Override
    public String toStringBasic() {
        return "Savings account" + "\n" +
                "Currency: " + getCurrency() + "\n" +
                "Balance: " + getBalance() + "\n";
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
    public void transfer(BigDecimal transferAmount, BankAccount destinationBankAccount) {
        if (!destinationBankAccount.getUserAccount().equals(getUserAccount())) {
            throw new IllegalArgumentException("Destination account is not associated to same user account!");
        }
        super.transfer(transferAmount, destinationBankAccount);
    }
}
