package MiMiA98.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SavingsAccountTest {

    SavingsAccount savingsAccount;

    @BeforeEach
    void setUp() {
        UserAccount userAccount = Mockito.mock(UserAccount.class);
        savingsAccount = new SavingsAccount(1, "123456", "RON", userAccount);
    }

    @Test
    void calculateInterest_after1Year_giveExpectedValue() {
        BigDecimal interestRate = BigDecimal.valueOf(0.02);
        BigDecimal period = BigDecimal.valueOf(1);

        savingsAccount.deposit(BigDecimal.valueOf(100));

        BigDecimal expected = BigDecimal.valueOf(2);
        BigDecimal actual = savingsAccount.getBalance().multiply(interestRate).multiply(period).stripTrailingZeros();
        assertEquals(expected, actual);
    }
}