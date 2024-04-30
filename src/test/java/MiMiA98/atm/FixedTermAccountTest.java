package MiMiA98.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FixedTermAccountTest {

    FixedTermAccount fixedTermAccount;
    UserAccount userAccount;

    @BeforeEach
    void setUp() {
        userAccount = Mockito.mock(UserAccount.class);
        fixedTermAccount = new FixedTermAccount("123456", "RON", 100.0, 1, userAccount);
    }

    @Test
    void transfer_beforeMaturityDate_throwsException() {
        BigDecimal transferAmount = BigDecimal.valueOf(10);
        BankAccount bankAccount = Mockito.mock(BankAccount.class);

        assertThrows(IllegalStateException.class, () -> fixedTermAccount.transfer(transferAmount, bankAccount));
    }

    @Test
    void transfer_afterMaturityDate_decreasesBalance() {
        fixedTermAccount = new FixedTermAccount("123456", "RON", 100.0, 0, userAccount);
        BankAccount bankAccount = Mockito.mock(BankAccount.class);
        BigDecimal transferAmount = BigDecimal.valueOf(10);

        fixedTermAccount.transfer(transferAmount, bankAccount);

        BigDecimal expected = BigDecimal.valueOf(90.0);
        BigDecimal actual = fixedTermAccount.getBalance();
        assertEquals(expected, actual);
    }

    @Test
    void deposit_anyAmount_throwsException() {
        BigDecimal depositAmount = BigDecimal.valueOf(100);

        assertThrows(IllegalStateException.class, () -> fixedTermAccount.deposit(depositAmount));
    }
}