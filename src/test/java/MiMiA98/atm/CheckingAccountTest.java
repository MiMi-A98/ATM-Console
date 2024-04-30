package MiMiA98.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CheckingAccountTest {

    CheckingAccount checkingAccount;

    @BeforeEach
    void setUp() {
        UserAccount userAccount = Mockito.mock(UserAccount.class);
        checkingAccount = new CheckingAccount("1", "USD", userAccount);
    }

    @Test
    void deposit_amountIsZero_throwsException() {
        BigDecimal depositAmount = BigDecimal.valueOf(0);
        assertThrows(IllegalArgumentException.class, () -> checkingAccount.deposit(depositAmount));
    }

    @Test
    void deposit_amountIsNegative_throwsException() {
        BigDecimal depositAmount = BigDecimal.valueOf(-100);
        assertThrows(IllegalArgumentException.class, () -> checkingAccount.deposit(depositAmount));
    }

    @Test
    void deposit_amountIsPositive_balanceIsUpdated() {
        BigDecimal depositAmount = BigDecimal.valueOf(100);
        BigDecimal originalBalance = checkingAccount.getBalance();

        checkingAccount.deposit(depositAmount);

        BigDecimal expected = originalBalance.add(depositAmount);
        BigDecimal actual = checkingAccount.getBalance();
        assertEquals(expected, actual);
    }

    @Test
    void withdraw_amountHigherThanBalance_throwsException() {
        BigDecimal withdrawAmount = checkingAccount.getBalance().add(BigDecimal.valueOf(100));
        assertThrows(IllegalArgumentException.class, () -> checkingAccount.withdraw(withdrawAmount));
    }

    @Test
    void withdraw_amountIsNegative_throwsException() {
        BigDecimal withdrawAmount = BigDecimal.valueOf(-100);
        assertThrows(IllegalArgumentException.class, () -> checkingAccount.withdraw(withdrawAmount));
    }

    @Test
    void withdraw_amountIsZero_throwsException() {
        BigDecimal withdrawAmount = BigDecimal.valueOf(0);
        assertThrows(IllegalArgumentException.class, () -> checkingAccount.withdraw(withdrawAmount));
    }

    @Test
    void withdraw_amountEqualToBalance_balanceIsZero() {
        checkingAccount.deposit(BigDecimal.valueOf(100));  // ensure that bank account balance > 0
        BigDecimal withdrawAmount = checkingAccount.getBalance();

        checkingAccount.withdraw(withdrawAmount);

        BigDecimal expected = BigDecimal.valueOf(0.0);
        BigDecimal actual = checkingAccount.getBalance();
        assertEquals(expected, actual);
    }

    @Test
    void transfer_amountHigherThanBalance_throwsException() {
        checkingAccount.deposit(BigDecimal.valueOf(100));
        BigDecimal transferAmount = BigDecimal.valueOf(1000);
        BankAccount bankAccount = Mockito.mock(BankAccount.class);
        assertThrows(IllegalArgumentException.class, () -> checkingAccount.transfer(transferAmount, bankAccount));
    }

    @Test
    void transfer_amountIsNegative_throwsException() {
        checkingAccount.deposit(BigDecimal.valueOf(100));
        BigDecimal transferAmount = BigDecimal.valueOf(-1);
        BankAccount bankAccount = Mockito.mock(BankAccount.class);
        assertThrows(IllegalArgumentException.class, () -> checkingAccount.transfer(transferAmount, bankAccount));
    }

    @Test
    void transfer_amountIsZero_throwsException() {
        checkingAccount.deposit(BigDecimal.valueOf(100));
        BigDecimal transferAmount = BigDecimal.valueOf(0);
        BankAccount bankAccount = Mockito.mock(BankAccount.class);
        assertThrows(IllegalArgumentException.class, () -> checkingAccount.transfer(transferAmount, bankAccount));
    }

    @Test
    void transfer_amountIsEqualToBalance_checkingAccountBalanceIsZero() {
        checkingAccount.deposit(BigDecimal.valueOf(100));
        BigDecimal transferAmount = BigDecimal.valueOf(100);
        BankAccount destinationBankAccount = Mockito.mock(BankAccount.class);

        checkingAccount.transfer(transferAmount, destinationBankAccount);

        BigDecimal expected = BigDecimal.valueOf(0.0);
        BigDecimal actual = checkingAccount.getBalance();
        assertEquals(expected, actual);
    }
}