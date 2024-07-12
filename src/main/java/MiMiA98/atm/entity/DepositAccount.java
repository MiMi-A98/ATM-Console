package MiMiA98.atm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class DepositAccount extends BankAccount {

    private double interestRate;
    private LocalDate dateOfCreation;

    protected DepositAccount() {
    }

    protected DepositAccount(String accountNumber, String currency, BigDecimal balance, double interestRate, UserAccount userAccount) {
        super(accountNumber, currency, balance, userAccount);
        this.interestRate = interestRate;
        this.dateOfCreation = LocalDate.now();
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

}
