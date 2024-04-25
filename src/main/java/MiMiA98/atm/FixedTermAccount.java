package MiMiA98.atm;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FixedTermAccount extends DepositAccount {

    private static final double INTEREST_RATE = 0.06;
    private final LocalDate dateOfCreation;
    private final LocalDate dateOfMaturity;

    public FixedTermAccount(int accountCode, String accountNumber, String currency, double balance, int timeOfMaturity, UserAccount userAccount) {
        super(accountCode, accountNumber, currency, balance, INTEREST_RATE, userAccount);
        this.dateOfCreation = LocalDate.now();
        this.dateOfMaturity = dateOfCreation.plusYears(timeOfMaturity);
    }

    public LocalDate getDateOfMaturity() {
        return dateOfMaturity;
    }

    @Override
    void setBalance(BigDecimal balance) {
        System.out.println("This account doesn't accept transfers!");
    }

    @Override
    public void transfer(BigDecimal transferAmount, BankAccount destinationBankAccount) {
        if (LocalDate.now().isBefore(dateOfMaturity)) {
            throw new IllegalStateException("Didn't reach day of maturity!");
        }

        super.transfer(transferAmount, destinationBankAccount);
    }

    @Override
    public String toString() {
        return "Fixed term account" + "\n" +
                "User name: " + getUserName() + "\n" +
                "Account number: " + getAccountNumber() + "\n" +
                "Currency: " + getCurrency() + "\n" +
                "Balance: " + getBalance() + "\n" +
                "Interest rate: " + INTEREST_RATE + "\n" +
                "Date of maturity: " + getDateOfMaturity() + "\n";
    }

}
