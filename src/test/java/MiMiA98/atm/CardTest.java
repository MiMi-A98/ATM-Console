package MiMiA98.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CardTest {

    Card card;

    @BeforeEach
    void setUp() {
        BankAccount bankAccount = Mockito.mock(BankAccount.class);
        card = new Card(1, "1234", bankAccount);
    }

    @Test
    void changePin_withNonDigitInput_throwsException() {
        String newPin = "12c4";
        assertThrows(IllegalArgumentException.class, () -> card.changePin(newPin));
    }

    @Test
    void changePin_withInvalidInputLength_throwsException() {
        String newPin = "12345";
        assertThrows(IllegalArgumentException.class, () -> card.changePin(newPin));
    }

    @Test
    void changePin_withFourDigitInput_notThrowException() {
        String newPin = "1234";
        assertDoesNotThrow(() -> card.changePin(newPin));
    }

}