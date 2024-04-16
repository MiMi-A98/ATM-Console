package MiMiA98.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;


class CardTest {

    Card card;

    @BeforeEach
    void setUp() {
        BankAccount bankAccount = Mockito.mock(BankAccount.class);
        card = new Card(1, "1234", bankAccount);
    }

    @Test
    void setPin_whenCardIsBlocked_throwsException() {
        card.setLocked(true);
        String newPin = "1234";
        assertThrows(IllegalStateException.class, () -> card.setPin(newPin));
    }

    @Test
    void setPin_cardIsNotBlocked_notThroesException() {
        String newPin = "1234";
        assertEquals(newPin, card.getPin());
    }

    @Test
    void setPin_withNonDigitInput_throwsException() {
        String newPin = "12c4";
        assertThrows(IllegalArgumentException.class, () -> card.setPin(newPin));
    }

    @Test
    void setPin_withInvalidInputLength_throwsException() {
        String newPin = "12345";
        assertThrows(IllegalArgumentException.class, () -> card.setPin(newPin));
    }

    @Test
    void setPin_withFourDigitInput_notThrowException() {
        String newPin = "1234";
        assertDoesNotThrow(() -> card.setPin(newPin));
    }

}