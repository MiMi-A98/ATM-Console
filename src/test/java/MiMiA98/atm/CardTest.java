package MiMiA98.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CardTest {

    Card card;

    @BeforeEach
    void setUp() {
        CheckingAccount checkingAccount = Mockito.mock(CheckingAccount.class);
        card = new Card("1", "1234", checkingAccount);
    }

    @Test
    void setPin_whenCardIsBlocked_throwsException() {
        card.setBlocked(true);
        String newPin = "1234";
        assertThrows(IllegalStateException.class, () -> card.setPin(newPin));
    }

    @Test
    void setPin_cardIsNotBlocked_changesPin() {
        String newPin = "1234";
        card.setPin(newPin);

        String expected = newPin;
        String actual = card.getPin();

        assertEquals(expected, actual);
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
    void setPin_withFourDigitInput_changesPin() {
        String newPin = "1234";

        card.setPin(newPin);

        String expected = newPin;
        String actual = card.getPin();

        assertEquals(expected, actual);
    }

}