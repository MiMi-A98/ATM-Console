package MiMiA98.atm;

import java.math.BigDecimal;
import java.util.Currency;

public abstract class BankAccount {
    private final String accountNumber;
    private final Currency currency;
    private final UserAccount userAccount;
    private BigDecimal balance;
    private boolean isFrozen;
    private boolean isClosed;

    protected BankAccount(String accountNumber, String currency, double balance, UserAccount userAccount) {
        this.accountNumber = accountNumber;
        this.currency = Currency.getInstance(currency);
        this.balance = BigDecimal.valueOf(balance);
        this.userAccount = userAccount;
        this.userAccount.addBankAccount(this);
    }

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

    public void transfer(BigDecimal transferAmount, BankAccount destinationBankAccount) {
        if (isClosed) {
            throw new IllegalStateException("Account is closed!");
        }
        if (isFrozen) {
            throw new IllegalStateException("Account is frozen!");
        }
        if (this.equals(destinationBankAccount)) {
            throw new IllegalArgumentException("Can't transfer money into the same account!");
        }

        if ((this.getBalance().compareTo(transferAmount)) >= 0 && (transferAmount.compareTo(BigDecimal.valueOf(0)) > 0)) {
            this.withdraw(transferAmount);
            destinationBankAccount.deposit(transferAmount);
        } else {
            throw new IllegalArgumentException("Provided withdraw amount is larger than balance!");
        }
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

    public BigDecimal getBalance() {
        return balance;
    }

    void setBalance(BigDecimal balance) {
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
