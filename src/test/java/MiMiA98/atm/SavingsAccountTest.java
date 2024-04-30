package MiMiA98.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SavingsAccountTest {

    SavingsAccount savingsAccount;
    UserAccount userAccount;

    @BeforeEach
    public void setUp() {
        userAccount = Mockito.mock(UserAccount.class);
        savingsAccount = new SavingsAccount("123456", "RON", userAccount);
    }

    @Test
    public void calculateInterest_after1Year_giveExpectedValue() {
        BigDecimal interestRate = BigDecimal.valueOf(0.02);
        BigDecimal period = BigDecimal.valueOf(1);

        savingsAccount.deposit(BigDecimal.valueOf(100));

        BigDecimal expected = BigDecimal.valueOf(2);
        BigDecimal actual = savingsAccount.getBalance().multiply(interestRate).multiply(period).stripTrailingZeros();
        assertEquals(expected, actual);
    }

    @Test
    void transfer_toNonOwnedAccount_throwsException() {
        UserAccount userAccount2 = new UserAccount("1234", "abc");
        BankAccount destinationBankAccount = new CheckingAccount("12345", "USD", userAccount2);
        BigDecimal transferAmount = BigDecimal.valueOf(10);

        assertThrows(IllegalArgumentException.class, () -> savingsAccount.transfer(transferAmount, destinationBankAccount));
    }

    @Test
    void transfer_toOwnedAccount_decreasesBalance() {
        BankAccount destinationBankAccount = new CheckingAccount("12345", "USD", userAccount);
        BigDecimal transferAmount = BigDecimal.valueOf(10);
        savingsAccount.deposit(BigDecimal.valueOf(100));

        savingsAccount.transfer(transferAmount, destinationBankAccount);

        BigDecimal expected = BigDecimal.valueOf(90.0);
        BigDecimal actual = savingsAccount.getBalance();

        assertEquals(expected, actual);
    }

}