package MiMiA98.atm;

import java.util.ArrayList;
import java.util.Collection;

public class CheckingAccount extends BankAccount {

    private final Collection<Card> cards;

    public CheckingAccount(String accountNumber, String currency, UserAccount userAccount) {
        super(accountNumber, currency, 0.0, userAccount);
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public void closeAccount() {
        super.closeAccount();
        for (Card card : cards) {
            card.setBlocked(true);
        }
    }

    @Override
    public String toStringBasic() {
        return "Checking account" + "\n" +
                "Currency: " + getCurrency() + "\n" +
                "Balance: " + getBalance() + "\n";
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
