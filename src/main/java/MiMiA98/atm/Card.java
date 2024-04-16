package MiMiA98.atm;

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

    public void changePin(String newPin) {
        if (newPin.matches("\\d{4}")) {
            this.pin = newPin;
        } else {
            throw new IllegalArgumentException("New pin doesn't fulfill the requirements!");
        }
    }
}
