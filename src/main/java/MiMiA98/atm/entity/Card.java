package MiMiA98.atm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Card {


    @Id
    private String cardNumber;
    @ManyToOne
    @JoinColumn(name = "checkingAccount_id")
    private CheckingAccount checkingAccount;
    private String pin;
    private boolean isBlocked;
    private boolean isActivated;

    public Card() {
    }

    public Card(String cardNumber, String pin, CheckingAccount checkingAccount) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.isBlocked = false;
        this.isActivated = false;
        this.checkingAccount = checkingAccount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String newPin) {
        this.pin = newPin;
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

    @Override
    public String toString() {
        return "CardNumber: " + cardNumber + "\n" +
                "Account: " + checkingAccount.getAccountNumber() + "\n" +
                "Pin: " + pin + "\n";
    }
}
