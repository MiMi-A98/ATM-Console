package MiMiA98.atm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class CheckingAccount extends BankAccount implements Transactional {

    private final Collection<Card> cards;

    public CheckingAccount(int accountCode, String accountNumber, String currency, UserAccount userAccount) {
        super(accountCode, accountNumber, currency, 0.0, userAccount);
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
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

    @Override
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

    @Override
    public void closeAccount() {
        super.closeAccount();
        for (Card card : cards) {
            card.setBlocked(true);
        }
    }

    @Override
    public String toString() {
        return "Checking account" + "\n" +
                "User name: " + getUserName() + "\n" +
                "Account number: " + getAccountNumber() + "\n" +
                "Currency: " + getCurrency() + "\n" +
                "Balance: " + getBalance() + "\n";
    }
}
