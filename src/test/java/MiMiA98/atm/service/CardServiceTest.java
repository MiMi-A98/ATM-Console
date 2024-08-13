package MiMiA98.atm.service;

import MiMiA98.atm.dao.CardDAO;
import MiMiA98.atm.entity.Card;
import MiMiA98.atm.entity.CheckingAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private CardDAO cardDAO;

    @InjectMocks
    private CardService cardService;

    @BeforeEach
    public void setUp() {
        cardService = new CardService(cardDAO);
    }

    @Test
    void createCard_correctInputs_passesMethodCallVerification() {
        String cardNumber = "card1";
        String pin = "1234";
        String checkingAccountId = "C1";

        cardService.createCard(cardNumber, pin, checkingAccountId);

        verify(cardDAO).createCard(cardNumber, pin, checkingAccountId);
    }

    @Test
    void createCard_nullCardNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> cardService.createCard(null, "1234", "c1"));
    }

    @Test
    void createCard_emptyCardNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> cardService.createCard("", "1234", "c1"));
    }

    @Test
    void createCard_nullPin_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> cardService.createCard("123456", null, "c1"));
    }

    @Test
    void createCard_emptyPin_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> cardService.createCard("123456", "", "c1"));
    }

    @Test
    void createCard_nullCheckingAccountId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> cardService.createCard("123456", "1234", null));
    }

    @Test
    void createCard_emptyCheckingAccountId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> cardService.createCard("123456", "1234", ""));
    }

    @Test
    void getCard_validCardNumber_passesMethodCallVerification() {
        String cardNumber = "card1";

        cardService.getCard(cardNumber);

        verify(cardDAO).readCard(cardNumber);
    }

    @Test
    void getCard_validCardNumber_resultNotNull() {
        String cardNumber = "card1";
        Card card = new Card();
        when(cardDAO.readCard(cardNumber)).thenReturn(card);

        Card result = cardService.getCard(cardNumber);

        assertNotNull(result);
    }

    @Test
    void getCard_nullCardNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> cardService.getCard(null));
    }

    @Test
    void getCard_emptyCardNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> cardService.getCard(""));
    }

    @Test
    void updateCardPin_validInputs_passesMethodCallVerification() {
        String cardNumber = "card1";
        String newPin = "1234";
        Card card = new Card(cardNumber, "0987", new CheckingAccount());
        when(cardDAO.readCard(cardNumber)).thenReturn(card);

        cardService.updateCardPin(cardNumber, newPin);

        verify(cardDAO).updateCardPin(cardNumber, newPin);
    }

    @Test
    void updateCardPin_invalidNewPin_throwsException() {
        String cardNumber = "card1";
        String newPin = "a234";
        Card card = new Card(cardNumber, "1234", new CheckingAccount());
        when(cardDAO.readCard(cardNumber)).thenReturn(card);

        assertThrows(IllegalArgumentException.class, () -> cardService.updateCardPin(cardNumber, newPin));
    }

    @Test
    void updateCardPin_nullPin_throwsException() {
        String cardNumber = "card1";
        assertThrows(IllegalArgumentException.class, () -> cardService.updateCardPin(cardNumber, null));
    }

    @Test
    void updateCardPin_emptyPin_throwsException() {
        String cardNumber = "card1";
        assertThrows(IllegalArgumentException.class, () -> cardService.updateCardPin(cardNumber, ""));
    }

    @Test
    void updateCardPin_nullCardNumber_throwsException() {
        String pin = "1234";
        assertThrows(IllegalArgumentException.class, () -> cardService.updateCardPin(null, pin));
    }

    @Test
    void updateCardPin_emptyCardNumber_throwsException() {
        String pin = "1234";
        assertThrows(IllegalArgumentException.class, () -> cardService.updateCardPin("", pin));
    }

    @Test
    void updateCardPin_cardBlocked_throwsException() {
        String cardNumber = "card1";
        String newPin = "a234";
        Card card = new Card(cardNumber, "1234", new CheckingAccount());
        card.setBlocked(true);
        when(cardDAO.readCard(cardNumber)).thenReturn(card);

        assertThrows(IllegalStateException.class, () -> cardService.updateCardPin(cardNumber, newPin));
    }

    @Test
    void updateCardBlockedState_validInputs_passesMethodCallVerification() {
        String cardNumber = "card1";
        boolean blockedState = true;
        Card card = new Card();
        when(cardDAO.readCard(cardNumber)).thenReturn(card);

        cardService.updateCardBlockedState(cardNumber, blockedState);

        verify(cardDAO).updateBlockedState(cardNumber, blockedState);
    }

    @Test
    void updateCardBlockedState_nullCardNumber_throwsException() {
        boolean blockedState = true;
        assertThrows(IllegalArgumentException.class, () -> cardService.updateCardBlockedState(null, blockedState));
    }

    @Test
    void updateCardBlockedState_emptyCardNumber_throwsException() {
        boolean blockedState = true;
        assertThrows(IllegalArgumentException.class, () -> cardService.updateCardBlockedState("", blockedState));
    }

    @Test
    void updateCardBlockedState_cardBlocked_throwsException() {
        String cardNumber = "card1";
        Card card = new Card(cardNumber, "1234", new CheckingAccount());
        card.setBlocked(true);
        when(cardDAO.readCard(cardNumber)).thenReturn(card);

        assertThrows(IllegalStateException.class, () -> cardService.updateCardBlockedState(cardNumber, true));
    }

    @Test
    void deleteCard_validCardNumber_passesMethodCallVerification() {
        String cardNumber = "card1";

        cardService.deleteCard(cardNumber);

        verify(cardDAO).deleteCard(cardNumber);
    }

    @Test
    void deleteCard_nullCardNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> cardService.deleteCard(null));
    }

    @Test
    void deleteCard_emptyCardNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> cardService.deleteCard(""));
    }

}