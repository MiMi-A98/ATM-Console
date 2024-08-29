package MiMiA98.atm.entity;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class SavingsAccount extends DepositAccount {

    private static double INTEREST_RATE = 0.02;
    private int timePeriod;

    public SavingsAccount() {

    }

    public SavingsAccount(String accountNumber, String currency, int timePeriod, UserAccount userAccount) {
        super(accountNumber, currency, BigDecimal.valueOf(0.0), INTEREST_RATE, userAccount);
        this.timePeriod = timePeriod;
    }

    public int getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(int timePeriod) {
        this.timePeriod = timePeriod;
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

}
