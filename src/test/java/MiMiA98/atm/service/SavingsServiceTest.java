package MiMiA98.atm.service;

import MiMiA98.atm.dao.SavingsDAO;
import MiMiA98.atm.entity.SavingsAccount;
import MiMiA98.atm.entity.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SavingsServiceTest {

    @Mock
    private SavingsDAO savingsDAO;

    @InjectMocks
    private SavingsService savingsService;

    @BeforeEach
    public void setUp() {
        savingsService = new SavingsService(savingsDAO);
    }

    @Test
    void doDeposit_validInputs_doesNotThrowError() {
        SavingsAccount savingsAccount = new SavingsAccount("savings1", "USD", 1, new UserAccount());
        BigDecimal depositAmount = BigDecimal.valueOf(100);

        assertDoesNotThrow(() -> savingsService.doDeposit(savingsAccount, depositAmount));
    }

    @Test
    void doWithdraw_validInputs_doesNotThrowError() {
        SavingsAccount savingsAccount = new SavingsAccount("savings1", "USD", 1, new UserAccount());
        BigDecimal withdrawAmount = BigDecimal.valueOf(100);

        assertDoesNotThrow(() -> savingsService.doDeposit(savingsAccount, withdrawAmount));
    }

    @Test
    void createSavingsAccount_validInputs_passesMethodCallVerification() {
        String accountNumber = "savings1";
        String currency = "USD";
        int timePeriod = 2;
        String userId = "user1";

        savingsService.createSavingsAccount(accountNumber, currency, timePeriod, userId);

        verify(savingsDAO).createSavingsAccount(accountNumber, currency, timePeriod, userId);
    }

    @Test
    void createSavingsAccount_nullAccountNumber_throwsException() {
        String currency = "USD";
        int timePeriod = 2;
        String userId = "user1";

        assertThrows(IllegalArgumentException.class, () -> savingsService.createSavingsAccount(null, currency, timePeriod, userId));
    }

    @Test
    void createSavingsAccount_emptyAccountNumber_throwsException() {
        String currency = "USD";
        int timePeriod = 2;
        String userId = "user1";

        assertThrows(IllegalArgumentException.class, () -> savingsService.createSavingsAccount("", currency, timePeriod, userId));
    }

    @Test
    void createSavingsAccount_nullCurrency_throwsException() {
        String accountNumber = "savings1";
        int timePeriod = 2;
        String userId = "user1";

        assertThrows(IllegalArgumentException.class, () -> savingsService.createSavingsAccount(accountNumber, null, timePeriod, userId));
    }

    @Test
    void createSavingsAccount_emptyCurrency_throwsException() {
        String accountNumber = "savings1";
        int timePeriod = 2;
        String userId = "user1";

        assertThrows(IllegalArgumentException.class, () -> savingsService.createSavingsAccount(accountNumber, "", timePeriod, userId));
    }

    @Test
    void createSavingsAccount_zeroTimePeriod_throwsException() {
        String accountNumber = "savings1";
        String currency = "USD";
        String userId = "user1";

        assertThrows(IllegalArgumentException.class, () -> savingsService.createSavingsAccount(accountNumber, currency, 0, userId));
    }

    @Test
    void createSavingsAccount_negativeTimePeriod_throwsException() {
        String accountNumber = "savings1";
        String currency = "USD";
        String userId = "user1";

        assertThrows(IllegalArgumentException.class, () -> savingsService.createSavingsAccount(accountNumber, currency, -1, userId));
    }

    @Test
    void createSavingsAccount_nullUserAccountNumber_throwsException() {
        String accountNumber = "savings1";
        String currency = "USD";
        int timePeriod = 2;

        assertThrows(IllegalArgumentException.class, () -> savingsService.createSavingsAccount(accountNumber, currency, timePeriod, null));
    }

    @Test
    void createSavingsAccount_emptyUserAccountNumber_throwsException() {
        String accountNumber = "savings1";
        String currency = "USD";
        int timePeriod = 2;

        assertThrows(IllegalArgumentException.class, () -> savingsService.createSavingsAccount(accountNumber, currency, timePeriod, ""));
    }

    @Test
    void getBankAccount_validAccountNumber_passesMethodCallVerification() {
        String accountNumber = "savings1";

        savingsService.getBankAccount(accountNumber);

        verify(savingsDAO).readSavingsAccount(accountNumber);
    }

    @Test
    void getSavingAccount_validAccountNumber_resultNotNull() {
        String accountNumber = "savings1";
        SavingsAccount savingsAccount = new SavingsAccount(accountNumber, "USD", 1, new UserAccount());
        when(savingsDAO.readSavingsAccount(accountNumber)).thenReturn(savingsAccount);

        SavingsAccount result = savingsService.getBankAccount(accountNumber);

        assertNotNull(result);
    }

    @Test
    void getSavingAccount_nullAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> savingsService.getBankAccount(null));
    }

    @Test
    void getSavingAccount_emptyAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> savingsService.getBankAccount(""));
    }

    @Test
    void updateSavingsAccountBalance_validInputs_passesMethodCallVerification() {
        String accountNumber = "savings1";
        BigDecimal balance = BigDecimal.valueOf(100);

        savingsService.updateBalance(accountNumber, balance);

        verify(savingsDAO).updateSavingsAccountBalance(accountNumber, balance);
    }

    @Test
    void updateSavingsAccountBalance_nullAccountNumber_throwsException() {
        BigDecimal balance = BigDecimal.valueOf(100);
        assertThrows(IllegalArgumentException.class, () -> savingsService.updateBalance(null, balance));
    }

    @Test
    void updateSavingsAccountBalance_emptyAccountNumber_throwsException() {
        BigDecimal balance = BigDecimal.valueOf(100);
        assertThrows(IllegalArgumentException.class, () -> savingsService.updateBalance("", balance));
    }

    @Test
    void updateSavingsAccountBalance_nullBalance_throwsException() {
        String accountNumber = "savings1";
        assertThrows(IllegalArgumentException.class, () -> savingsService.updateBalance(accountNumber, null));
    }

    @Test
    void updateSavingsAccountBalance_negativeBalance_throwsException() {
        String accountNumber = "savings1";
        BigDecimal balance = BigDecimal.valueOf(-100);
        assertThrows(IllegalArgumentException.class, () -> savingsService.updateBalance(accountNumber, balance));
    }

    @Test
    void updateSavingsAccountInterestRate_validInputs_passesMethodCallValidation() {
        String accountNumber = "savings1";
        double interestRate = 0.4;

        savingsService.updateSavingsAccountInterestRate(accountNumber, interestRate);

        verify(savingsDAO).updateSavingsAccountInterestRate(accountNumber, interestRate);
    }

    @Test
    void updateSavingsAccountInterestRate_nullAccountNumber_throwsException() {
        double interestRate = 0.4;
        assertThrows(IllegalArgumentException.class, () -> savingsService.updateSavingsAccountInterestRate(null, interestRate));
    }

    @Test
    void updateSavingsAccountInterestRate_emptyAccountNumber_throwsException() {
        double interestRate = 0.4;
        assertThrows(IllegalArgumentException.class, () -> savingsService.updateSavingsAccountInterestRate("", interestRate));
    }

    @Test
    void updateSavingsAccountInterestRate_zeroInterestRate_throwsException() {
        String accountNumber = "savings1";
        assertThrows(IllegalArgumentException.class, () -> savingsService.updateSavingsAccountInterestRate(accountNumber, 0.0));
    }

    @Test
    void updateSavingsAccountInterestRate_negativeInterestRate_throwsException() {
        String accountNumber = "savings1";
        assertThrows(IllegalArgumentException.class, () -> savingsService.updateSavingsAccountInterestRate(accountNumber, -0.5));
    }

    @Test
    void updateSavingsAccountTimePeriod_validInputs_passesMethodCallVerification() {
        String accountNumber = "savings1";
        int timePeriod = 1;

        savingsService.updateSavingsAccountTimePeriod(accountNumber, timePeriod);

        verify(savingsDAO).updateSavingsAccountTimePeriod(accountNumber, timePeriod);
    }

    @Test
    void updateSavingsAccountTimePeriod_nullAccountNumber_throwsException() {
        int timePeriod = 1;
        assertThrows(IllegalArgumentException.class, () -> savingsService.updateSavingsAccountTimePeriod(null, timePeriod));
    }

    @Test
    void updateSavingsAccountTimePeriod_emptyAccountNumber_throwsException() {
        int timePeriod = 1;
        assertThrows(IllegalArgumentException.class, () -> savingsService.updateSavingsAccountTimePeriod("", timePeriod));
    }

    @Test
    void updateSavingsAccountTimePeriod_zeroTimePeriod_throwsException() {
        String accountNumber = "savings1";
        assertThrows(IllegalArgumentException.class, () -> savingsService.updateSavingsAccountTimePeriod(accountNumber, 0));
    }

    @Test
    void updateSavingsAccountTimePeriod_negativeTimePeriod_throwsException() {
        String accountNumber = "savings1";
        int timePeriod = -1;
        assertThrows(IllegalArgumentException.class, () -> savingsService.updateSavingsAccountTimePeriod(accountNumber, timePeriod));
    }

    @Test
    void updateSavingsAccountFrozenState_validInputs_passesMethodCallVerification() {
        String accountNumber = "savings1";
        boolean frozenState = false;

        savingsService.updateSavingsAccountFrozenState(accountNumber, frozenState);

        verify(savingsDAO).updateSavingsAccountFrozenState(accountNumber, frozenState);
    }

    @Test
    void updateSavingsAccountFrozenState_nullAccountNumber_throwsException() {
        boolean frozenState = false;
        assertThrows(IllegalArgumentException.class, () -> savingsService.updateSavingsAccountFrozenState(null, frozenState));
    }

    @Test
    void updateSavingsAccountFrozenState_emptyAccountNumber_throwsException() {
        boolean frozenState = false;
        assertThrows(IllegalArgumentException.class, () -> savingsService.updateSavingsAccountFrozenState("", frozenState));
    }

    @Test
    void updateSavingsAccountClosedState_validInputs_passesMethodCallVerification() {
        String accountNumber = "savings1";

        savingsService.updateSavingsAccountClosedState(accountNumber);

        verify(savingsDAO).updateSavingsAccountClosedState(accountNumber);
    }

    @Test
    void updateSavingsAccountClosedState_nullAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> savingsService.updateSavingsAccountClosedState(null));
    }

    @Test
    void updateSavingsAccountClosedState_emptyAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> savingsService.updateSavingsAccountClosedState(""));
    }

    @Test
    void deleteSavingsAccount_validAccountNumber_passesMethodCallVerification() {
        String accountNumber = "savings1";

        savingsService.deleteSavingsAccount(accountNumber);

        verify(savingsDAO).deleteSavingsAccount(accountNumber);
    }

    @Test
    void deleteSavingAccount_nullAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> savingsService.deleteSavingsAccount(null));
    }

    @Test
    void deleteSavingAccount_emptyAccountNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> savingsService.deleteSavingsAccount(""));
    }
}