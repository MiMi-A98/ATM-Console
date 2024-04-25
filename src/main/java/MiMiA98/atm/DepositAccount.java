package MiMiA98.atm;

import java.math.BigDecimal;

public abstract class DepositAccount extends BankAccount {

    private final double interestRate;

    protected DepositAccount(int accountCode, String accountNumber, String currency, double balance, double interestRate, UserAccount userAccount) {
        super(accountCode, accountNumber, currency, balance, userAccount);
        this.interestRate = interestRate;
    }


    public BigDecimal calculateInterest(double periodOfTime) {
        BigDecimal interest = BigDecimal.valueOf(interestRate);
        BigDecimal period = BigDecimal.valueOf(periodOfTime);
        BigDecimal interestAmount = getBalance().multiply(interest);
        return interestAmount.multiply(period);
    }

}
