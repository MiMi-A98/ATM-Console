package MiMiA98.atm;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FixedTermAccount extends DepositAccount {

    private static final double INTEREST_RATE = 0.06;
    private final LocalDate dateOfCreation;
    private final LocalDate dateOfMaturity;

    public FixedTermAccount(String accountNumber, String currency, double balance, int timeOfMaturity, UserAccount userAccount) {
        super(accountNumber, currency, balance, INTEREST_RATE, userAccount);
        this.dateOfCreation = LocalDate.now();
        this.dateOfMaturity = dateOfCreation.plusYears(timeOfMaturity);
    }

    public FixedTermAccount(String accountNumber, String currency, int timeOfMaturity, UserAccount userAccount) {
        this(accountNumber, currency, 0.0, timeOfMaturity, userAccount);
    }

    public boolean isMatured() {
        return LocalDate.now().isAfter(dateOfMaturity) || LocalDate.now().isEqual(dateOfMaturity);
    }

    @Override
    public void deposit(BigDecimal depositAmount) {
        if (getBalance() != null || getBalance().compareTo(BigDecimal.valueOf(0)) > 0) {
            throw new IllegalStateException("Can't deposit money in an initialized fix term account");
        }
        super.deposit(depositAmount);
    }

    @Override
    public void withdraw(BigDecimal withdrawAmount) {
        throw new IllegalStateException("Can't withdraw from fix term account");
    }

    @Override
    public void transfer(BigDecimal transferAmount, BankAccount destinationBankAccount) {
        if (LocalDate.now().isBefore(dateOfMaturity)) {
            throw new IllegalStateException("Didn't reach day of maturity!");
        }
        if (isClosed()) {
            throw new IllegalStateException("Account is closed!");
        }
        if (isFrozen()) {
            throw new IllegalStateException("Account is frozen!");
        }
        if (this.equals(destinationBankAccount)) {
            throw new IllegalArgumentException("Can't transfer money into the same account!");
        }

        if ((this.getBalance().compareTo(transferAmount)) == 0 && (transferAmount.compareTo(BigDecimal.valueOf(0)) > 0)) {
            this.setBalance(getBalance().subtract(transferAmount));
            destinationBankAccount.deposit(transferAmount);
            closeAccount();
        } else {
            throw new IllegalArgumentException("Provided withdraw amount is lower than zero or lower than balance!");
        }
    }

    public LocalDate getDateOfMaturity() {
        return dateOfMaturity;
    }

    @Override
    public String toStringBasic() {
        return "Fixed term account" + "\n" +
                "Currency: " + getCurrency() + "\n" +
                "Balance: " + getBalance() + "\n" +
                "Date of maturity: " + getDateOfMaturity() + "\n";
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
