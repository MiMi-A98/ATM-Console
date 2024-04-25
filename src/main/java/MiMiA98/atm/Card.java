package MiMiA98.atm;

public class Card {

    private final String cardNumber;
    private final CheckingAccount checkingAccount;
    private String pin;
    private boolean isBlocked;
    private boolean isActivated;


    public Card(String cardNumber, String pin, CheckingAccount checkingAccount) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.isBlocked = false;
        this.isActivated = false;
        this.checkingAccount = checkingAccount;
        this.checkingAccount.addCard(this);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String newPin) {
        if (isBlocked) {
            throw new IllegalStateException("Card is blocked!");
        } else {
            if (newPin.matches("\\d{4}")) {
                this.pin = newPin;
            } else {
                throw new IllegalArgumentException("New pin doesn't fulfill the requirements!");
            }
        }
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        if (!checkingAccount.isClosed()) {
            isBlocked = blocked;
        } else {
            throw new IllegalStateException("Bank account is closed!");
        }
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public CheckingAccount getCheckingAccount() {
        return checkingAccount;
    }
}
