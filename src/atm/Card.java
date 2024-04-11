package atm;

public class Card {

    private int cardNumber;
    private String pin;
    private BankAccount bankAccount;

    public Card(int cardNumber, String pin, BankAccount bankAccount) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.bankAccount = bankAccount;
        bankAccount.addCard(this);
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }
}
