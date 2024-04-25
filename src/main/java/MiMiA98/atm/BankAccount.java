package MiMiA98.atm;

import java.math.BigDecimal;
import java.util.Currency;

public abstract class BankAccount {
    private final int accountCode;
    private final String accountNumber;
    private final Currency currency;
    private final UserAccount userAccount;
    private BigDecimal balance;
    private boolean isFrozen;
    private boolean isClosed;

    protected BankAccount(int accountCode, String accountNumber, String currency, double balance, UserAccount userAccount) {
        this.accountNumber = accountNumber;
        this.currency = Currency.getInstance(currency);
        this.balance = BigDecimal.valueOf(balance);
        this.userAccount = userAccount;
        this.accountCode = accountCode;
        this.userAccount.addBankAccount(accountCode, this);
    }

    public void transfer(BigDecimal transferAmount, BankAccount destinationBankAccount) {
        if (isClosed) {
            throw new IllegalStateException("Account is closed!");
        }
        if (this.equals(destinationBankAccount)) {
            throw new IllegalArgumentException("Can't transfer money into the same account!");
        }


        if ((this.getBalance().compareTo(transferAmount)) >= 0 && (transferAmount.compareTo(BigDecimal.valueOf(0)) > 0)) {
            this.setBalance(this.getBalance().subtract(transferAmount));
            destinationBankAccount.setBalance(transferAmount.add(getBalance()));
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

}
