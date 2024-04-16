package MiMiA98.atm;

import java.util.ArrayList;
import java.util.Collection;

public class BankAccount {
    private int accountNumber;
    private String currency;
    private double balance;
    private UserAccount userAccount;
    private Collection<Card> cards;

    public BankAccount(int accountNumber, String currency, double balance, UserAccount userAccount) {
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.balance = balance;
        this.userAccount = userAccount;
        userAccount.addBankAccount(this);
        cards = new ArrayList<>();
    }

    public BankAccount(int accountNumber, String currency, UserAccount userAccount) {
        this(accountNumber, currency, 0, userAccount);
    }

    public double getBalance() {
        return balance;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void deposit(double depositAmount) {
        if (depositAmount > 0 ) {
            this.balance += depositAmount;
        } else {
            throw new IllegalArgumentException("Provided deposit amount is less than zero!");
        }

    }

    public void withdraw(double withdrawAmount) {
        if (balance >= withdrawAmount && withdrawAmount > 0) {
            this.balance = balance - withdrawAmount;
        } else {
            throw new IllegalArgumentException("Provided withdraw amount is larger than balance!");
        }
    }


}