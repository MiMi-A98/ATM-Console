package MiMiA98.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        UserAccount userAccount = Mockito.mock(UserAccount.class);
        bankAccount = new BankAccount(1, "USD", userAccount);
    }

    @Test
    void deposit_amountIsZero_throwsException() {
        int depositAmount = 0;
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(depositAmount));
    }

    @Test
    void deposit_amountIsNegative_throwsException() {
        int depositAmount = -100;
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(depositAmount));
    }

    @Test
    void deposit_amountIsPositive_notThrowsException() {
        int depositAmount = 100;
        assertDoesNotThrow(() -> bankAccount.deposit(depositAmount));
    }

    @Test
    void withdraw_amountHigherThanBalance_throwsException() {
        double withdrawAmount = bankAccount.getBalance() + 100;
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(withdrawAmount));
    }

    @Test
    void withdraw_amountIsNegative_throwsException() {
        double withdrawAmount = -100;
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(withdrawAmount));
    }

    @Test
    void withdraw_amountIsZero_throwsException() {
        double withdrawAmount = 0;
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(withdrawAmount));
    }

    @Test
    void withdraw_amountEqualToBalance_throwsException() {
        bankAccount.deposit(100);  // ensure that bank account balance > 0
        double withdrawAmount = bankAccount.getBalance();
        assertDoesNotThrow(() -> bankAccount.withdraw(withdrawAmount));
    }
}