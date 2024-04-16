package MiMiA98.atm;

public class Card {

    private int cardNumber;
    private String pin;
    private boolean isLocked;

    private BankAccount bankAccount;


    public Card(int cardNumber, String pin, BankAccount bankAccount) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.isLocked = false;
        this.bankAccount = bankAccount;
        bankAccount.addCard(this);
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String newPin) {
        if (isLocked) {
            throw new IllegalStateException("Card is blocked!");
        } else {
            if (newPin.matches("\\d{4}")) {
                this.pin = newPin;
            } else {
                throw new IllegalArgumentException("New pin doesn't fulfill the requirements!");
            }
        }
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }
}
