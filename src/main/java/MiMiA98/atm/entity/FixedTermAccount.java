package MiMiA98.atm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "fixed_term_account")
public class FixedTermAccount extends DepositAccount {

    private static double INTEREST_RATE = 0.06;
    private LocalDate dateOfMaturity;

    public FixedTermAccount() {
    }

    public FixedTermAccount(String accountNumber, String currency, int timeOfMaturity, UserAccount userAccount) {
        this(accountNumber, currency, BigDecimal.valueOf(0.0), timeOfMaturity, userAccount);
    }

    public FixedTermAccount(String accountNumber, String currency, BigDecimal balance, int timeOfMaturity, UserAccount userAccount) {
        super(accountNumber, currency, balance, INTEREST_RATE, userAccount);
        this.dateOfMaturity = getDateOfCreation().plusYears(timeOfMaturity);
    }

    public LocalDate getDateOfMaturity() {
        return dateOfMaturity;
    }

    public void setTimeOfMaturity(int timeOfMaturity) {
        this.dateOfMaturity = getDateOfCreation().plusYears(timeOfMaturity);
    }

    public boolean isMatured() {
        return LocalDate.now().isAfter(getDateOfMaturity()) || LocalDate.now().isEqual(getDateOfMaturity());
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
