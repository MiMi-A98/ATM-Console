package MiMiA98.atm.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Currency;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BankAccount {

    @Id
    private String accountNumber;
    private Currency currency;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;
    private BigDecimal balance;
    private boolean isFrozen = false;
    private boolean isClosed = false;

    protected BankAccount() {
    }

    protected BankAccount(String accountNumber, String currency, BigDecimal balance, UserAccount userAccount) {
        this.accountNumber = accountNumber;
        this.currency = Currency.getInstance(currency);
        this.balance = balance;
        this.userAccount = userAccount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getUserName() {
        return userAccount.getUserName();
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void closeAccount() {
        if (!isClosed) {
            isClosed = true;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String toStringBasic() {
        return "";
    }
}
