package MiMiA98.atm.service;

import MiMiA98.atm.dao.CardDAO;
import MiMiA98.atm.entity.Card;

public class CardService {
    private final CardDAO cardDAO = new CardDAO();

    public void createCard(String cardNumber, String pin, String checkingAccountId) {
        if (cardNumber == null || cardNumber.isEmpty()
                || pin == null || pin.isEmpty()
                || checkingAccountId == null || checkingAccountId.isEmpty()) {
            throw new IllegalArgumentException("All necessary information should be completed!");
        }
        cardDAO.createCard(cardNumber, pin, checkingAccountId);
    }

    public Card getCard(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            throw new IllegalArgumentException("Card number cannot be null or empty");
        }
        return cardDAO.readCard(cardNumber);
    }

    public void updateCardPin(String cardNumber, String newPIN) {
        if (cardNumber == null || cardNumber.isEmpty() || newPIN == null || newPIN.isEmpty()) {
            throw new IllegalArgumentException("Card number/New PIN cannot be null or empty");
        }

        Card card = cardDAO.readCard(cardNumber);
        if (card.isBlocked()) {
            throw new IllegalStateException("Card is blocked!");
        } else {
            if (newPIN.matches("\\d{4}")) {
                cardDAO.updateCardPin(cardNumber, newPIN);
            } else {
                throw new IllegalArgumentException("New pin doesn't fulfill the requirements!");
            }
        }
    }

    public void updateCardBlockedState(String cardNumber, boolean isBlocked) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            throw new IllegalArgumentException("Card number cannot be null or empty");
        }
        cardDAO.updateBlockedState(cardNumber, isBlocked);
    }

    public void deleteCard(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            throw new IllegalArgumentException("Card number cannot be null or empty");
        }
        cardDAO.deleteCard(cardNumber);

    }
}
